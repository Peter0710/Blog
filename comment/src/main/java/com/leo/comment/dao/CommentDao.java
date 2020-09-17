package com.leo.comment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Liu
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment> {
}
