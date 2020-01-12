package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserListInfoService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Sunday, January 2020
 */

@Slf4j
@Controller
@RequestMapping("/users/list")
public class UserListInfoController {

	private UserListInfoService userListInfoService;

	private UserService userService;

	private PostService postService;

	@Autowired
	public UserListInfoController(UserListInfoService userListInfoService, UserService userService,
			PostService postService) {
		this.userListInfoService = userListInfoService;
		this.userService = userService;
		this.postService = postService;
	}

	@GetMapping
	public String showUserListInfoTable(Model model) {
		List<User> users = userListInfoService.findAll();
		log.info("Successfully founded all Users except Admin");

		model.addAttribute("users", users);
		return "user-list-info/user-list-info";
	}

	@GetMapping("/{username}/info")
	public String showChosenUserInfoForm(@PathVariable String username, Model model) {
		User chosenUser = userService.findByUserName(username);
		log.info("Successfully founded User info for User with username: `{}`", username);
		model.addAttribute("user", chosenUser);
		model.addAttribute("userUsername", chosenUser.getUserName());

		List<User> users = userService.findAll();
		log.info("Successfully founded all Users.");
		model.addAttribute("users", users);

		Map<Long, String> userProfileImages = userService.mapAllProfileImages(users);
		log.info("Successfully mapped all UserProfile images.");
		model.addAttribute("userProfileImages", userProfileImages);

		int numberOfPostsForUser = userListInfoService.getNumberOfPostsForUser(username);
		log.info("Successfully founded all Posts for searched User with username: `{}`", username);
		model.addAttribute("numberOfPostsForUser", numberOfPostsForUser);

		int numberOfCommentsForUser = userListInfoService.getNumberOfCommentsForUser(username);
		log.info("Successfully founded all Comments for searched User with username: `{}`", username);
		model.addAttribute("numberOfCommentsForUser", numberOfCommentsForUser);

		int numberOfDownloadImagesForUser = userListInfoService.getNumberOfDownloadImagesForUser(username);
		log.info("Successfully founded all downloaded images for searched User with username: `{}`", username);
		model.addAttribute("numberOfDownloadImagesForUser", numberOfDownloadImagesForUser);

		int numberOfUploadedImagesWithCurrentPackage = userListInfoService.getNumberOfUploadedImagesWithCurrentPackageForUser(username);
		log.info("Successfully founded all uploaded images with current package for searched User with username: `{}`", username);
		model.addAttribute("numberOfUploadedImagesWithCurrentPackage", numberOfUploadedImagesWithCurrentPackage);

		long numberOfUploadedImageSizeWithCurrentPackage = userListInfoService.getNumberOfUploadedImageSizeWithCurrentPackageForUser(username);
		log.info("Successfully founded all uploaded image size with current package for searched User with username: `{}`", username);
		model.addAttribute("numberOfUploadedImageSizeWithCurrentPackage", numberOfUploadedImageSizeWithCurrentPackage);

		return "user-list-info/user-info";
	}

	@GetMapping("/{id}/delete")
	public String deleteUserFromList(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		postService.deleteAllUserPosts(id);
		log.info("Successfully deleted all Posts for User with id: `{}`", id);

		User chosenUser = userListInfoService.delete(id);
		log.info("Successfully delete User with username: `{}`", chosenUser.getUserName());

		redirectAttributes.addFlashAttribute("deleteUserMessage", "You have successfully deleted User with id: " + id);
		return "redirect:/users/list";
	}
}
