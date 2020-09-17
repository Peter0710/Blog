package com.leo.item.service;

import com.leo.item.entity.Chapter;

import java.util.List;

/**
 * @author Liu
 */
public interface ChapterService {
    List<Chapter> getChapter();

    Integer addChapter(Chapter chapter);

    Integer removeChapter(String id);
}
