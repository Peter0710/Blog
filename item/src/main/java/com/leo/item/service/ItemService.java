package com.leo.item.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.item.entity.Item;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author Liu
 */
public interface ItemService {

    Item getItemById(Integer id);

    Integer removeItem(String id);

    Integer addItem(Item item);

    List<Item> getItemByType(String id);

    List<Item> getItemByUserId(Page<Item> page, String id);

    List<Item> getAllItem(Page<Item> page);

    Map<String, Object> getItemDetails(String id, HttpServletRequest request) throws ExecutionException, InterruptedException, Exception;

    Map<Item, Integer> getViewTop();
}
