package com.leo.schedule.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.schedule.entity.Item;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Liu
 */
@Mapper
public interface ItemDao extends BaseMapper<Item> {
}
