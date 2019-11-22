package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.impl.PackageServiceImpl;
import com.luv2code.imageuploader.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user-package-option")
public class PackageController {

	private final PackageServiceImpl packageService;

	private final UserServiceImpl userService;

	public PackageController(PackageServiceImpl packageService, UserServiceImpl userService) {
		this.packageService = packageService;
		this.userService = userService;
	}

	@GetMapping
	private String showUserPackageOptionForm(Model model, Principal principal) {
		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());
		if (user.getUserPackage() != null) {
			log.info("User `{}` already has a Package.", user.getUserName());
			return "redirect:/home-page";
		}

		List<Package> packages = packageService.findAll();
		model.addAttribute("packages", packages);
		log.info("Successfully fetched all Packages.");

		return "user-package/user-package-form";
	}

	@GetMapping("/{packageId}")
	private String saveUserPackageOptionForm(@PathVariable("packageId") Long packageId, Principal principal, Model model) {
		String username = principal.getName();
		log.info("Saving Package option for User with username: `{}`.", username);
		User user = userService.choosePackageOption(packageId, principal.getName());
		log.info("Successfully saved Package option with id `{}` to User with username `{}`.", packageId, username);

		model.addAttribute("user", user);
		return "redirect:/home-page";
	}
}
