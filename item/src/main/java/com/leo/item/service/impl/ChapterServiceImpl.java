package com.leo.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leo.item.dao.ChapterDao;
import com.leo.item.entity.Chapter;
import com.leo.item.service.ChapterService;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Liu
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterDao chapterDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    //会产生堆外内存溢出
    //spring 2.0 默认使用lettuce操作redis, 是使用netty进行网络通信
    //lettuce会导致netty堆外内存溢出，netty不指定堆外内存，会默认使用-Xmx300m
    //只调大堆外内存不能解决问题
    //解决方法1： 升级lettuce， 2， 切换使用jedis
    //解决高并发下的缓存问题，缓存穿透，缓存击穿，缓存雪崩问题

    //使用缓存，指定缓存使用的key,指定缓存的存活时间，将数据保存为json格式(自定义缓存管理器)
    //key指定缓存使用的主键，
//    @Caching() 组合多个缓存注解操作
    @Cacheable(value = {"chapter"},key = "'chapterkey'")
    @Override
    public List<Chapter> getChapter() {
        //查询缓存是否有数据
        //空结果缓存，解决缓存穿透
        //随机过期时间，解决缓存雪崩
        //加锁，解决缓存击穿
        //保证锁的释放非常麻烦。。。。
        //要保证原子操作，获取锁时，要设置过期时间，释放锁的过程也要时原子的
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", "lock");
//        if(lock){
//            //获取锁成功
//            //释放锁
//        }else {
//            //获取锁失败，可以使用自旋方式获取锁
//        }
        //获取的是非公平锁

//        RLock lock = redissonClient.getLock("lock");
        //获取公平锁
//        redissonClient.getFairLock("lock");
        //
        //实现了锁的自动续期，lock.lock()阻塞式的等待
        //自动为锁加长时间，为业务添加执行时间
        //加锁的业务运行完成，不会给锁继续续期，即使不主动释放锁，也不会产生死锁问题
        //有一个看门狗的时间，如果不指定过期时间，
        //默认为看门狗时间30秒，业务每过10秒，会自动进行一次续期，重新设置过期时间为30秒

        //最佳选择是用我们自定义的锁时间，省掉续期的时间
        //lock.lock(时间， TimeUnit.second)
        //读写锁
//        RReadWriteLock abc = redissonClient.getReadWriteLock("abc");
//        abc.readLock();
//        abc.writeLock()

//        RSemaphore a = redissonClient.getSemaphore("a");
//        a.trySetPermits(5);
//        a.tryAcquire();//非阻塞的获取，返回布尔值，
//        //信号量可以作为分布式的限流

//        a.release();
//        a.acquire();阻塞方法，获取不到，一直阻塞
        //获取闭锁，countdownlatch

//        RCountDownLatch lock = redissonClient.getCountDownLatch("lock");
//        lock.trySetCount(5);
//        lock.await();
//        lock.countDown();
        List<Chapter> list = chapterDao.selectList(null);
        return list;

//        String chapter = (String)redisTemplate.opsForValue().get("chapter");
//        if (StringUtils.isEmpty(chapter)){
//            List<Chapter> list = chapterDao.selectList(null);
//            //用json转换数据，方便跨语言使用
//            String string = JSON.toJSONString(list);
//            redisTemplate.opsForValue().set("chapter",string,1, TimeUnit.DAYS);
//            return list;
//        }
//        //转换数据从json到对象
//        List<Chapter> chapters = JSON.parseObject(chapter, new TypeReference<List<Chapter>>() {
//        });
//        return chapters;
    }

    @Override
    public Integer addChapter(Chapter chapter) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<Chapter>().eq("chapter", chapter.getChapter());
        if(chapterDao.selectOne(chapterQueryWrapper)!=null){
            return 0;
        }
        return chapterDao.insert(chapter);
    }

    @Override
    public Integer removeChapter(String id) {
        return chapterDao.deleteById(id);
    }
}
