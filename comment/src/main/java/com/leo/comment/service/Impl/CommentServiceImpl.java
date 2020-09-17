package com.leo.comment.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leo.comment.dao.CommentDao;
import com.leo.comment.entity.Comment;
import com.leo.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liu
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> getComment(Page<Comment> page, String id) {
        Page<Comment> commentPage = commentDao.selectPage(page, null);
        List<Comment> parentList = commentPage.getRecords();
        long pages = commentPage.getPages();
        long total = commentPage.getTotal();
        List<Comment> list = parentList.stream().filter(comment -> comment.getTextId() == Integer.parseInt(id))
                .map(comment -> {
                    comment.setReply(getChildrenComment(parentList,comment.getId()));
                    return comment;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean deleteComment(String id) {
        //TODO 删除该评论的子评论
        commentDao.deleteById(Integer.parseInt(id));
        return true;
    }

    @Override
    public boolean addComment(Comment comment) {
        //TODO
        int insert = commentDao.insert(comment);
        if(insert==0){
            return false;
        }
        return true;
    }


    public List<Comment> getChildrenComment(List<Comment> parentList, Long id){
        List<Comment> childrenList = parentList.stream().filter(comment -> comment.getParentId().equals(id))
                .collect(Collectors.toList());
        return childrenList;
    }
}
