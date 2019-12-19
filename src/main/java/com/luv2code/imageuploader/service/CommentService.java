package com.luv2code.imageuploader.service;

import java.util.List;

import com.luv2code.imageuploader.entity.Comment;

/**
 * Created by lzugaj on Thursday, December 2019
 */

public interface CommentService {

    Comment save(String description, String username, Long postId);

    List<Comment> findAll(Long postId);

    int numberOfCommentsForPost(Long postId);

}
