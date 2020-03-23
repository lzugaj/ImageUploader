package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Comment;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.CommentService;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import com.luv2code.imageuploader.utils.MessageSuccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Thursday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/post")
public class CommentController {

    private final CommentService commentService;

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/comment/{postId}")
    public String submitCommentToPost(@PathVariable Long postId, @RequestParam("commentDescription") String description,
                                      Principal principal, RedirectAttributes redirectAttributes) {
        Comment newComment = commentService.save(description, principal.getName(), postId);
        log.info("Successfully create new Comment for `{}` with id: `{}`", principal.getName(), newComment.getId());

        redirectAttributes.addFlashAttribute("createdNewComment", principal.getName() + MessageSuccess.SUCCESSFULLY_ADDED_COMMENT);
        return "redirect:/home";
    }

    @GetMapping("/{postId}/comments")
    public String showAllCommentsForPost(@PathVariable Long postId, Model model) {
        List<Comment> comments = commentService.findAll(postId);
        log.info("Successfully founded all Comments for Post id: `{}`", postId);
        model.addAttribute("comments", comments);

        Post selectedPost = postService.findById(postId);
        log.info("Successfully founded Post with id: `{}`", postId);
        model.addAttribute("selectedPost", selectedPost);

        User postCreator = selectedPost.getUser();
        log.info("Successfully founded User that create selected Post with username: `{}`", postCreator.getUserName());
        model.addAttribute("postCreator", postCreator);

        int dayOfMonth = selectedPost.getDateOfPost().getDayOfMonth();
        model.addAttribute("dayOfMonth", dayOfMonth);

        String selectedPostImage = postService.getSelectedPostImage(selectedPost);
        model.addAttribute("selectedPostImage", selectedPostImage);

        String postHashTags = selectedPost.getHashTag();
        String formattedString = postService.formatHashTags(postHashTags);
        model.addAttribute("postHashTags", formattedString);

        List<User> users = userService.findAll();
        log.info("Successfully founded all Users.");
        model.addAttribute("users", users);

        Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
        log.info("Successfully mapped all UserProfile images.");
        model.addAttribute("userProfileImages", userProfileImages);

        return "post-comments/post-comments.html";
    }
}
