package com.leo.item.feign;

import com.leo.commonuse.common.CommonResult;
import com.leo.item.entity.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Liu
 */
@FeignClient("comment")
public interface CommentFeignService {

    @GetMapping("/comment/get/{id}")
    List<Comment> getComment(@PathVariable String id, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum);
}
