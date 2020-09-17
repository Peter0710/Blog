package com.leo.item.service;

import com.leo.item.entity.Type;

import java.util.List;

public interface TypeService {
    List<Type> getType();

    Integer addType(Type type);

    Integer deleteType(String id);
}
