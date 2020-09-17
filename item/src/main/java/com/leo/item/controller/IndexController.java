package com.leo.item.controller;

import com.leo.commonuse.common.CommonResult;
import com.leo.item.entity.Chapter;
import com.leo.item.entity.Item;
import com.leo.item.feign.StarFeignService;
import com.leo.item.service.ChapterService;
import com.leo.item.service.IndexService;
import com.leo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Liu
 */
@RestController
@RequestMapping
public class IndexController {

    @Autowired
    IndexService indexService;


    /**
     * 查看首页信息
     * 主要包含查导航栏（使用redis 缓存）
     * 查redis中点赞信息
     *
     * @return
     */
    @GetMapping("/")
    public CommonResult getIndex() throws ExecutionException, InterruptedException {
        Map<String, Object> map = indexService.getDetail();
        return CommonResult.ok().data("chapter", map);
    }
}
