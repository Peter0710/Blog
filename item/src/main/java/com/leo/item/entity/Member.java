package com.leo.item.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Liu
 */
@Data
public class Member implements Serializable {


    public Long id;

    public String name;

    public String password;

    private String third;
}
