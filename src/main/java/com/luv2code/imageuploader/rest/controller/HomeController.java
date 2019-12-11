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

import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class HomeController {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public HomeController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    @RequestMapping("/home")
    public String indexPage(Model model) {
        List<Post> posts = postService.findAll();
        log.info("Successfully founded all Posts.");
        model.addAttribute("posts", posts);

        Map<Long, String> postImages = postService.mapAllPostImages(posts);
        log.info("Successfully mapped all Post images.");
        model.addAttribute("postImages", postImages);

        Map<Long, String> hashTags = postService.mapHashTags(posts);
        log.info("Successfully mapped all HashTag for Post images.");
        model.addAttribute("hashTags", hashTags);

        Map<Long, Integer> daysOfMonth = postService.mapDateOfAllPosts(posts);
        log.info("Successfully mapped all day of month foreach Post.");
        model.addAttribute("daysOfMonth", daysOfMonth);

        List<User> users = userService.findAll();
        log.info("Successfully founded all Users.");
        model.addAttribute("users", users);

        Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
        log.info("Successfully mapped all UserProfile images.");
        model.addAttribute("userProfileImages", userProfileImages);

        return "home/index";
    }
}
