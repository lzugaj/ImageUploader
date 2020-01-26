package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.Comment;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.CommentRepository;
import com.luv2code.imageuploader.service.CommentService;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzugaj on Thursday, December 2019
 */

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    @Autowired
    @Lazy
    public CommentServiceImpl(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public Comment save(String description, String username, Long postId) {
        User user = userService.findByUserName(username);
        log.info("Successfully founded User with username: `{}`", username);

        Post commentedPost = postService.findById(postId);
        log.info("Successfully founded Post with id: `{}`", postId);

        Comment newComment = new Comment();
        setUpVariables(newComment, description, user, commentedPost);
        log.info("Setting up variables for new Comment");

        commentRepository.save(newComment);
        log.info("Saving new Comment to Post(`{}`) with id: `{}`", commentedPost.getId(), newComment.getId());
        return newComment;
    }

    private static void setUpVariables(Comment newComment, String description, User user, Post commentedPost) {
        newComment.setDescription(description);
        newComment.setNumberOfLikes(0);
        newComment.setNumberOfDislikes(0);
        newComment.setDateOfPost(LocalDateTime.now());
        newComment.setPost(commentedPost);
        newComment.setUser(user);
    }

    @Override
    public List<Comment> findAll(Long postId) {
        List<Comment> comments = commentRepository.findAll();
        log.info("Successfully founded all Comments");

        List<Comment> foundedComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getPost().getId().equals(postId)) {
                foundedComments.add(comment);
            }
        }

        return foundedComments;
    }

    @Override
    public int numberOfCommentsForPost(Long postId) {
        List<Comment> comments = findAll(postId);
        int numberOfComments = comments.size();
        log.info("Successfully founded `{}` comments for Post with id: `{}`", numberOfComments, postId);

        return numberOfComments;
    }
}
