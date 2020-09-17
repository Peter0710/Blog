package com.leo.item.service.impl;

import com.leo.item.entity.Chapter;
import com.leo.item.entity.Item;
import com.leo.item.feign.StarFeignService;
import com.leo.item.service.ChapterService;
import com.leo.item.service.IndexService;
import com.leo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Liu
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    ChapterService chapterService;

    @Autowired
    StarFeignService starFeignService;

    @Autowired
    ItemService itemService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> getDetail() throws ExecutionException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        //查询标签
        CompletableFuture<List<Chapter>> chapterFuture = CompletableFuture.supplyAsync(() -> {
            List<Chapter> chapter = chapterService.getChapter();
            return chapter;
        }, threadPoolExecutor);
        //获取最多浏览的文章
        CompletableFuture<Map<Item, Integer>> viewFuture = CompletableFuture.supplyAsync(() -> {
            Map<Item, Integer> viewTop = itemService.getViewTop();
            return viewTop;
        }, threadPoolExecutor);

        CompletableFuture.allOf(chapterFuture, viewFuture);

        map.put("chapter", chapterFuture.get());
        map.put("view", viewFuture.get());

        return map;
    }
}
