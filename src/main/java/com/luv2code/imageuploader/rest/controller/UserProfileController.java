package com.luv2code.imageuploader.rest.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public UserProfileController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/{username}")
    private String showUserProfileInfo(@PathVariable("username") String username, Model model) {
        User searchedUser = userService.findByUserName(username);
        log.info("Successfully founded User with username: `{}`", username);

        List<Post> userPosts = postService.findAllForUser(searchedUser);
        log.info("Successfully founded all Posts for User with username: `{}`", username);

        Map<Long, String> postImages = new HashMap<>();
        for (Post post : userPosts) {
            byte[] postImage = Base64.getEncoder().encode(post.getPostImage());
            String imageUrl = new String(postImage, StandardCharsets.UTF_8);
            postImages.put(post.getId(), imageUrl);
        }

        model.addAttribute("user", searchedUser);
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("postImages", postImages);
        return "user-profile/user-profile";
    }

}
