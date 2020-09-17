package com.leo.comment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.comment.entity.Comment;
import com.leo.comment.service.CommentService;
import com.leo.commonuse.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liu
 */
@Controller()
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;


    /**
     * 添加评论
     * @param comment
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public CommonResult addComment(@RequestBody Comment comment){
        //判断是否登录

        boolean comment1 = commentService.addComment(comment);
        if (!comment1){
            return CommonResult.error();
        }
        return CommonResult.ok();
    }

    /**
     * 删除评论
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/delete/{id}")
    public CommonResult deleteComment(@PathVariable String id){
        boolean deleteComment = commentService.deleteComment(id);
        if (!deleteComment){
            return CommonResult.error();
        }
        return CommonResult.ok();
    }

    /**
     * 获取页面中文章的评论，用于远程接口调用
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/get/{id}")
    public List<Comment> getComment(@PathVariable String id, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Page<Comment> pageInfo = new Page<>(pageNum, 5);
        List<Comment> comment = commentService.getComment(pageInfo, id);
        return comment;
    }

}
