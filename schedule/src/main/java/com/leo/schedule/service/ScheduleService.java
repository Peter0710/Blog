package com.leo.schedule.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leo.commonuse.common.CommonParam;
import com.leo.schedule.dao.ItemDao;
import com.leo.schedule.dao.StarDao;
import com.leo.schedule.entity.Item;
import com.leo.schedule.entity.Star;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Set;

/**
 * @author Liu
 */
@EnableAsync        // 2.开启多线程
@Component
public class ScheduleService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StarDao starDao;

    @Autowired
    ItemDao itemDao;

    private static Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    /**
     * fixedRate：定义一个按一定频率执行的定时任务
     * cron：通过表达式来配置任务执行时间
     * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。按顺序依次为：
     *
     * 秒（0~59）
     * 分钟（0~59）
     * 3 小时（0~23）
     * 4 天（0~31）
     * 5 月（0~11）
     * 6 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     * 年份（1970－2099）
     */

    @Async
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduledStar(){
        Set<ZSetOperations.TypedTuple<Object>> StarSet = redisTemplate.opsForZSet().rangeWithScores(CommonParam.REDIS_COUNT, 0, -1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = StarSet.iterator();
        while(iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("star value:"+next.getValue()+"star score:"+next.getScore());
            Long textId = JSON.parseObject(next.getValue().toString(), Long.class);
            Long score = next.getScore().longValue();
            Star txt_id = starDao.selectOne(new QueryWrapper<Star>().eq("txt_id", textId));
            Star star = new Star();
            star.setTxtId(textId);
            star.setStarCount(score);
            if (txt_id == null){
                starDao.insert(star);
                return;
            }
            starDao.update(star, new QueryWrapper<Star>().eq("txt_id", textId));
        }
    }
    @Async
    @Scheduled(cron = "0/5 * * * * *")
    public void scheduledView(){
        Set<ZSetOperations.TypedTuple<Object>> ViewSet = redisTemplate.opsForZSet().rangeWithScores(CommonParam.VIEW_COUNT, 0, -1);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = ViewSet.iterator();
        while(iterator.hasNext()){
            ZSetOperations.TypedTuple<Object> next = iterator.next();
            System.out.println("view value:"+next.getValue()+"view score:"+next.getScore());
            Item item = JSON.parseObject(next.getValue().toString(), Item.class);
            Integer score = next.getScore().intValue();
            item.setViewCount(score);
            itemDao.updateById(item);
        }
    }


}
