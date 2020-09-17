package com.leo.item.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
@TableName("blog_chapter")
public class Chapter implements Serializable {

    private Long id;

    private String chapter;
}
