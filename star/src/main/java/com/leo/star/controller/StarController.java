package com.leo.star.controller;

import com.leo.commonuse.common.CommonResult;
import com.leo.star.service.impl.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Liu
 */
@RestController
@RequestMapping("/star")
public class StarController {

    @Autowired
    StarService starService;

    @GetMapping("/like")
    public CommonResult likeItem(@RequestParam("id") String id, @RequestParam("uid") String uid){
        starService.likeArticle(id, uid);
        return CommonResult.ok();
    }

    /**
     * 判断用户是否点赞
     * @param id
     * @param uid
     * @return
     */
    @GetMapping("/isliked")
    public boolean isLike(@RequestParam("id") String id, @RequestParam("uid") String uid){
        Boolean bool = starService.checkLike(id, uid);
        return bool;
    }

    /**
     * 获取文章点赞数
     * @param id
     * @return
     */
    @GetMapping("/likecount/{id}")
    public Double likeCount(@PathVariable String id){
        Double number = starService.countLiked(id);
        return number;
    }
}
