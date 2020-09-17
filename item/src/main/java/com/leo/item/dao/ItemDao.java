package com.leo.item.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.item.entity.Item;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Liu
 */
@Mapper
public interface ItemDao extends BaseMapper<Item> {
}
