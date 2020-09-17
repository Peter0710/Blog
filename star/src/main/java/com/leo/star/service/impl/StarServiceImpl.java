package com.leo.star.service.impl;

import com.leo.commonuse.common.CommonParam;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Liu
 */
@Service
public class StarServiceImpl implements StarService{


    @Autowired
    RedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 点赞功能，已经点赞再次带你赞会取消点赞
     * 未点赞会点赞
     * @param id
     * @param uid
     */
    @Override
    public void likeArticle(String id, String uid) {
        //获取对应的redis数据结构 文章--点赞用户  文章--点赞数，不存在则创建，
        //检查用户是否点赞
        String set = CommonParam.REDIS_MEMBER + id;
        if (redisTemplate.opsForSet().isMember(set, uid)){
            //已经点赞则取消点赞，删除文章--点赞用户表中的用户id, 点赞数-1
            redisTemplate.opsForSet().remove(set, uid);
            redisTemplate.opsForZSet().incrementScore(CommonParam.REDIS_COUNT, id, -1);

        }else {
            //未点赞，则添加点赞用户id, 点赞数+1
            redisTemplate.opsForSet().add(set, uid);
            redisTemplate.opsForZSet().incrementScore(CommonParam.REDIS_COUNT, id, 1);
        }
    }


    /**
     * 用于页面点击时，显示用户是否已经点赞
     * @param id
     * @param uid
     * @return
     */
    @Override
    public Boolean checkLike(String id, String uid) {
        //检查文章--点赞用户表中数据，如果在表中，返回true,否则返回false
        String set = CommonParam.REDIS_MEMBER + id;
        return redisTemplate.opsForSet().isMember(set, uid);
    }

    /**
     * 获取点赞人数
     * @param id
     * @return
     */
    @Override
    public Double countLiked(String id) {
        //如果有表，则查询表中点赞数
        Double score = redisTemplate.opsForZSet().score(CommonParam.REDIS_COUNT, id);
        return score;
    }

}
