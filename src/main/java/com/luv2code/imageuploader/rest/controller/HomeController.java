package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;

    private final PostService postService;

    @Autowired
    public HomeController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public String indexPage(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        log.info("Successfully founded User with username: `{}`.", user.getUserName());

        List<Post> posts = postService.findAll();
        log.info("Successfully founded all Posts.");
        model.addAttribute("posts", posts);

        Map<Long, String> postImages = new HashMap<>();
        int dayOfMonth = 0;
        for (Post post : posts) {
            byte[] postImage = Base64.getEncoder().encode(post.getPostImage());
            String imageUrl = new String(postImage, StandardCharsets.UTF_8);
            postImages.put(post.getId(), imageUrl);
            dayOfMonth = post.getDateOfPost().getDayOfMonth();
        }

        model.addAttribute("dayOfMonth", dayOfMonth);
        model.addAttribute("postImages", postImages);
        model.addAttribute("user", user);
        return "home/index";
    }
}
