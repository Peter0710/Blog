package com.leo.star.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
@TableName("blog_star")
public class Star implements Serializable {

    @TableId
    private Long id;

    private Long uid;

    private Long textId;
}
