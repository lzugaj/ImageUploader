package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.UserListInfoService;
import com.luv2code.imageuploader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@Autowired
	public UserListInfoController(UserListInfoService userListInfoService, UserService userService) {
		this.userListInfoService = userListInfoService;
		this.userService = userService;
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

		return "user-list-info/user-info";
	}
}
