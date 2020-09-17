package com.leo.item.service.impl;

import com.alibaba.fastjson.JSON;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.commonuse.common.CommonParam;
import com.leo.item.dao.ItemDao;
import com.leo.item.entity.Comment;
import com.leo.item.entity.Item;
import com.leo.item.entity.Member;
import com.leo.item.feign.CommentFeignService;
import com.leo.item.feign.StarFeignService;
import com.leo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Liu
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Qualifier("mainThreadPool")
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    CommentFeignService commentFeignService;

    @Autowired
    StarFeignService starFeignService;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public Item getItemById(Integer id) {
        return itemDao.selectById(id);
    }

    @Override
    public Integer removeItem(String id) {
        int i = itemDao.deleteById(id);
        if (i == 0) {
            return  null;
        }
        return i;
    }

    @Override
    public Integer addItem(Item item) {
        //TODO 加入ES
        //TODO 加入到redis中进行发布时间的排序
        //用于首页显示最新推荐
       return itemDao.insert(item);
    }

    @Override
    public List<Item> getItemByType(String id) {
            int i = Integer.parseInt(id);
            return itemDao.selectList(new QueryWrapper<Item>().eq("type_id", i));

    }

    @Override
    public List<Item> getItemByUserId(Page<Item> page, String id) {
            int i = Integer.parseInt(id);
            Page<Item> itemPage = itemDao.selectPage(page, new QueryWrapper<Item>().eq("uid", i));
            List<Item> records = itemPage.getRecords();
            return records;
    }

    @Override
    public List<Item> getAllItem(Page<Item> page) {
        Page<Item> itemPage = itemDao.selectPage(page, null);
        List<Item> records = itemPage.getRecords();
        return records;
    }

    @Override
    public Map<String, Object> getItemDetails(String id, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //查看文章详情
        CompletableFuture<Item> itemFuture = CompletableFuture.supplyAsync(() -> {
            Item item = getItemById(Integer.parseInt(id));
            return item;
        }, threadPoolExecutor);
        //查看评论
        CompletableFuture<List<Comment>> commentFuture = CompletableFuture.supplyAsync(() -> {
            List<Comment> comment = commentFeignService.getComment(id, 1);
            return comment;
        }, threadPoolExecutor);
        //查看点赞数
        CompletableFuture<Integer> starFuture = CompletableFuture.supplyAsync(() -> {
            Double i = starFeignService.likeCount(id);
            if (i == null){
                return 0;
            }
            return i.intValue();
        }, threadPoolExecutor);
        //浏览数+1
        CompletableFuture<Integer> viewFuture = CompletableFuture.supplyAsync(() -> {
            Item view = getItemById(Integer.parseInt(id));
            Double score = redisTemplate.opsForZSet().incrementScore(CommonParam.VIEW_COUNT, view, 1);
            return score.intValue() + 1;
        }, threadPoolExecutor);
        //TODO 通过session查看用户是否登录，登录则查看用户是否点赞
        //先判断用户是否登录，登录后session中会保存相关用户对象的信息，如果未i登录则直接显示false
        //如果登录了，就去点赞模块查询redis数据，查询文章id的存储表中是否又该用户id
        //如果存在则说明用户已经点赞，否则表示用户未点赞
        CompletableFuture<Boolean> booleanFuture = CompletableFuture.supplyAsync(() -> {
            Member attribute = (Member) request.getSession().getAttribute(CommonParam.SESSIONKEY);
            if (attribute == null) {
                return false;
            }
            boolean like = starFeignService.isLike(id, attribute.getId().toString());
            return like;
        }, threadPoolExecutor);

        CompletableFuture.allOf(itemFuture, commentFuture, starFuture, viewFuture, booleanFuture);

        map.put("item", itemFuture.get());
        map.put("comment", commentFuture.get());
        map.put("star", starFuture.get());
        map.put("view", viewFuture.get());
        map.put("liked", booleanFuture.get());
        return map;
    }

    /**
     * 获取前10浏览排名的文章显示在首页，通过zset进行排名，获取结果通过迭代器，遍历后封装成一个map,
     * ！！！！
     * @return
     */
    @Override
    public  Map<Item, Integer> getViewTop() {
        Map<Item, Integer> map = new HashMap<>();
        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().reverseRangeWithScores(CommonParam.VIEW_COUNT, 0, 9);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
        while(iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("value:"+next.getValue()+" score:"+next.getScore());
            Item item = JSON.parseObject(next.getValue().toString(), Item.class);
            Integer score = next.getScore().intValue();
            map.put(item, score);
        }
        return map;
    }
}
