package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.entity.UserProfile;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserProfileService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/profile")
public class UserProfileController {

    private final UserService userService;

    private final PostService postService;

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserService userService, PostService postService, UserProfileService userProfileService) {
        this.userService = userService;
        this.postService = postService;
        this.userProfileService = userProfileService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/{username}")
    private String showUserProfileInfo(@PathVariable("username") String username, Model model, Principal principal) {
        User searchedUser = userService.findByUserName(username);
        log.info("Successfully founded User with username: `{}`", username);
        model.addAttribute("user", searchedUser);
        model.addAttribute("userUsername", searchedUser.getUserName());

        List<Post> userPosts = postService.findAllForUser(searchedUser);
        log.info("Successfully founded all Posts for User with username: `{}`", username);
        model.addAttribute("userPosts", userPosts);

        Map<Long, String> postImages = postService.mapAllPostImages(userPosts);
        log.info("Successfully mapped all Post images for User with username: `{}`.", username);
        model.addAttribute("postImages", postImages);

        int numberOfUserPosts = postService.findAllForUser(searchedUser).size();
        log.info("Successfully founded `{}` for User with username `{}`", numberOfUserPosts, username);
        model.addAttribute("numberOfUserPosts", numberOfUserPosts);

		List<User> users = userService.findAll();
		log.info("Successfully founded all Users.");
		model.addAttribute("users", users);

		Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
		log.info("Successfully mapped all UserProfile images.");
		model.addAttribute("userProfileImages", userProfileImages);

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

		List<User> users = userService.findAll();
		log.info("Successfully founded all Users.");
		model.addAttribute("users", users);

		Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
		log.info("Successfully mapped all UserProfile images.");
		model.addAttribute("userProfileImages", userProfileImages);

        return "user-profile/selected-profile-post";
    }

    @GetMapping("/show/update/form")
    private String showUpdateUserProfileForm(Model model, Principal principal) {
        model.addAttribute("newUserProfile", new UserProfile());
        User searchedUserProfile = userProfileService.findUserProfileByUsername(principal.getName());
        model.addAttribute("searchedUserProfile", searchedUserProfile);

        return "user-profile/update-user-profile-form";
    }

    @PostMapping("/submit/update/form")
    public String saveUserPostForm(@RequestParam("userProfileImage") MultipartFile userProfileImage, Principal principal) throws IOException {
        User updatedUser = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", updatedUser.getUserName());

		UserProfile userProfile = userProfileService.save(updatedUser, userProfileImage);
		log.info("Successfully updated UserProfile for User with username: `{}`", userProfile.getUser().getUserName());

        return "redirect:/user/profile/show/update/form";
    }
}
