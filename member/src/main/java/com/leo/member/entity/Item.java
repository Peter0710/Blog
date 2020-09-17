package com.leo.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Liu
 */
@Data
public class Item implements Serializable {

    private Long id;

    private String title;

    private String name;

    private String content;

    private Date time;

    private Long uid;

    private Long typeId;

    private long viewCount;
}
