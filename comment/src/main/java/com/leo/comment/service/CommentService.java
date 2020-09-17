package com.leo.comment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.comment.entity.Comment;

import java.util.List;

/**
 * @author Liu
 */
public interface CommentService {
    List<Comment> getComment(Page<Comment> page, String id);

    boolean deleteComment(String id);

    boolean addComment(Comment comment);

}
