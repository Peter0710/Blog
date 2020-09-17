package com.leo.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leo.item.dao.ItemDao;
import com.leo.item.dao.TypeDao;
import com.leo.item.entity.Item;
import com.leo.item.entity.Type;
import com.leo.item.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Liu
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;

    @Autowired
    ItemDao itemDao;

    @Override
    public List<Type> getType() {
        return typeDao.selectList(null);
    }

    @Override
    public Integer addType(Type type) {

        return typeDao.insert(type);
    }

    @Override
    public Integer deleteType(String id) {

        QueryWrapper<Item> typeId = new QueryWrapper<Item>().eq("typeId", id);
        itemDao.delete(typeId);
        return typeDao.deleteById(id);
    }
}
