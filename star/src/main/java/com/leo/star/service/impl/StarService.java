package com.leo.star.service.impl;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author Liu
 */

public interface StarService {

    void likeArticle(String id, String uid);


    Boolean checkLike(String id, String uid);

    Double countLiked(String id);

}
