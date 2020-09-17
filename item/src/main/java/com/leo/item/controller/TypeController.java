package com.leo.item.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leo.item.entity.Item;
import com.leo.item.entity.Type;
import com.leo.item.service.ItemService;
import com.leo.item.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liu
 */
@RestController("/type")
public class TypeController {

    @Autowired
    TypeService typeService;

    @Autowired
    ItemService itemService;

    @GetMapping("type/{id}")
    public List<Item> getTypeItem(@PathVariable(required = true) String id){
        //TODO 分页查询和显示
        return itemService.getItemByType(id);
    }

    @GetMapping("/getType")
    public List<Type> getType(){
        return typeService.getType();
    }

    @PostMapping("/addType")
    public Integer addType(@RequestBody Type type){
        return typeService.addType(type);
    }

    @GetMapping("/delete/{id}")
    public Integer deleteType(@PathVariable String id){
        return typeService.deleteType(id);
    }

}
