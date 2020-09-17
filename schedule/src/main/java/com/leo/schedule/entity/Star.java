package com.leo.schedule.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Liu
 */
@Data
@TableName("blog_starcount")
public class Star {

    private Long txtId;

    private Long starCount;
}
