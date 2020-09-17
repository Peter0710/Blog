package com.leo.comment.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Liu
 */
@Data
@TableName("blog_comment")
public class Comment implements Serializable {

    @TableId
    private Long id;

    private Long uid;

    private Date time;

    private String comment;

    private Long parentId;

    private Long textId;

    @TableField(exist = false)
    private List<Comment> reply = new ArrayList<>();
}
