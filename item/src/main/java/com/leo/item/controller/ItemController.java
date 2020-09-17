package com.leo.item.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.commonuse.common.CommonResult;
import com.leo.item.entity.Item;
import com.leo.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Liu
 */
@RestController("/item")
public class ItemController {

    @Autowired
    ItemService itemService;


    /**
     * 根据文章id删除文章，用于后台管理
     * 或者用户自己删除文章
     * @param id
     * @return
     */
    @GetMapping("/remove/{id}")
    public CommonResult removeItem(@PathVariable String id){
        //TODO 删除文章评论
        //删除文章点赞数
        Integer integer = itemService.removeItem(id);
        if (integer == null){
            return CommonResult.error().message("delete fail");
        }
        return CommonResult.ok().message("delete success");
    }

    /**
     * 添加文章
     * @param item
     * @return
     */
    @PostMapping("/addItem")
    public CommonResult addItem(@RequestBody Item item){
        item.setViewCount(0);
        Integer integer = itemService.addItem(item);
        return CommonResult.ok().message("add success");
    }

    /**
     * 根据用户id获取所有文章
     * @param id
     * @return
     */
    @GetMapping("/getItemByUserId/{id}")
    public List<Item> getItemByUserId(@PathVariable String id,
                                      @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum) throws Exception {
        Page<Item> pageInfo = new Page<>(pageNum, 5);
        List<Item> result = itemService.getItemByUserId(pageInfo,id);
        return result;
    }


    /**
     * 获取所有文章
     * @return
     */
    @GetMapping("/getAll")
    public CommonResult getAllItem(@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        Page<Item> pageInfo = new Page<>(pageNum, 5);
        List<Item> allItem = itemService.getAllItem(pageInfo);
        return CommonResult.ok().data("data", allItem);
    }

    /**
     * 查看文章详情页，包括文章相求，包含用户的简单信息，这一部分冗余在text表中
     * 查看评论（远程调用）
     * 查看点赞数
     * 设计服务降级，防止恶意刷浏览数？？？？
     *
     * @param id
     * @return
     */
    @GetMapping("/getItemDetail/{id}")
    public CommonResult getItemDetail(@PathVariable String id, HttpServletRequest request) throws Exception {
        Map<String, Object> result = itemService.getItemDetails(id, request);
        return CommonResult.ok().data(result);
    }

    @GetMapping("/getViewTop")
    public CommonResult getViewTop(){
        Map<Item, Integer> viewTop = itemService.getViewTop();
        return CommonResult.ok().data("top", viewTop);
    }
}
