package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/home")
public class HomeController {

    private final PostService postService;

    private final UserService userService;

    private final CacheManager cacheManager;

    @Autowired
    public HomeController(PostService postService, UserService userService, CacheManager cacheManager) {
        this.postService = postService;
        this.userService = userService;
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public String indexPage(Model model) {
        for(String name : cacheManager.getCacheNames()){
            Objects.requireNonNull(cacheManager.getCache(name)).clear();
        }

        checkIfUserIsAnonymousOrLoggedIn(model);

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

        // TODO: Delete?
        Map<Long, User> usersMap = userService.mapAllUsers(users);
        log.info("Successfully mapped all Users.");
        model.addAttribute("usersMap", usersMap);

        // TODO: Delete?
        Map<Long, Post> postsMap = postService.mapAllPosts(posts);
        log.info("Successfully mapped all UserProfile images.");
        model.addAttribute("postsMap", postsMap);

        return "home/index";
    }

    private void checkIfUserIsAnonymousOrLoggedIn(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (!username.equals("anonymousUser")) {
            User loggedInUser = userService.findByUserName(username);
            log.info("Successfully founded logged in User with username: `{}`", loggedInUser.getUserName());
            model.addAttribute("loggedInUser", loggedInUser);
            if (loggedInUser.getUserProfile().getProfileImage() != null) {
                byte[] userProfileImage = Base64.getEncoder().encode(loggedInUser.getUserProfile().getProfileImage());
                String imageUrl = new String(userProfileImage, StandardCharsets.UTF_8);
                model.addAttribute("imageUrl", imageUrl);
            }
        } else {
            model.addAttribute("anonymousUser", username);
        }
    }
}
