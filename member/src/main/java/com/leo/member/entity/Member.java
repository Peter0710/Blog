package com.leo.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
@TableName("blog_member")
public class Member implements Serializable {

    @TableId
    public Long id;

    public String name;

    public String password;

    private String third;
}
