package com.leo.item.feign;

import com.leo.commonuse.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author Liu
 */
@FeignClient(name = "star")
public interface StarFeignService {

    @GetMapping("/star/isliked")
    boolean isLike(@RequestParam("id") String id, @RequestParam("uid") String uid);

    @GetMapping("/star/likecount/{id}")
    Double likeCount(@PathVariable String id);




}
