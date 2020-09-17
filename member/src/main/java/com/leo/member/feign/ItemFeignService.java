package com.leo.member.feign;

import com.leo.member.entity.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Liu
 */
@FeignClient(name = "item")
public interface ItemFeignService {

    @GetMapping("/item/getItemByUserId/{id}")
    List<Item> getItemByUserId(@PathVariable String id, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum);
}
