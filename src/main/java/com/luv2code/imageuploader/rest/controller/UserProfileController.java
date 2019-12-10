package com.luv2code.imageuploader.rest.controller;

import java.security.Principal;
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
    private String showUserProfileInfo(@PathVariable("username") String username, Model model, Principal principal) {
        User searchedUser = userService.findByUserName(username);
        log.info("Successfully founded User with username: `{}`", username);
        model.addAttribute("user", searchedUser);

        List<Post> userPosts = postService.findAllForUser(searchedUser);
        log.info("Successfully founded all Posts for User with username: `{}`", username);
        model.addAttribute("userPosts", userPosts);

        Map<Long, String> postImages = postService.mapAllPostImages(userPosts);
        log.info("Successfully mapped all Post images for User with username: `{}`.", username);
        model.addAttribute("postImages", postImages);

        int numberOfUserPosts = postService.findAllForUser(searchedUser).size();
        log.info("Successfully founded `{}` for User with username `{}`", numberOfUserPosts, username);
        model.addAttribute("numberOfUserPosts", numberOfUserPosts);

        if (principal.getName().equals(username)) {
            boolean isThisProfileFromLoggedInUser = true;
            model.addAttribute("isThisProfileFromLoggedInUser", isThisProfileFromLoggedInUser);
        }

        return "user-profile/user-profile";
    }

    @GetMapping("/post/{id}")
    private String showUserProfileSelectedPost(@PathVariable("id") Long id, Model model) {
        Post selectedPost = postService.findById(id);
        log.info("Successfully founded Post with id: `{}`", id);
        model.addAttribute("selectedPost", selectedPost);

        User postCreator = postService.findById(id).getUser();
        log.info("Successfully founded User that create this Post with username: `{}`", postCreator.getUserName());
        model.addAttribute("postCreator", postCreator);

        int dayOfMonth = selectedPost.getDateOfPost().getDayOfMonth();
        model.addAttribute("dayOfMonth", dayOfMonth);

        String selectedPostImage = postService.getSelectedPostImage(selectedPost);
        model.addAttribute("selectedPostImage", selectedPostImage);

        String postHashTags = selectedPost.getHashTag();
        String formattedString = postService.formatHashTags(postHashTags);
        model.addAttribute("postHashTags", formattedString);

        return "user-profile/selected-profile-post";
    }
}
