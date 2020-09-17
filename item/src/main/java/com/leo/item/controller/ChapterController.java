package com.leo.item.controller;

import com.leo.item.entity.Chapter;
import com.leo.item.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Liu
 */
@RestController("/chapter")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @GetMapping("/getChapter")
    public List<Chapter> getChapter(){
        return chapterService.getChapter();
    }

    @PostMapping("/addChapter")
    public Integer addChapter(@RequestBody Chapter chapter){
        return chapterService.addChapter(chapter);
    }

    @PostMapping("/removeChapter/{id}")
    public Integer removeChapter(@PathVariable String id){
        return chapterService.removeChapter(id);
    }
}
