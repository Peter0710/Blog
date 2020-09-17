package com.leo.item.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
@TableName("blog_type")
public class Type implements Serializable {

    @TableId
    private Long id;

    private String type;
}
