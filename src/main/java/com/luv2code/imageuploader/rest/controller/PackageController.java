package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.ImageFormatService;
import com.luv2code.imageuploader.service.PackageService;
import com.luv2code.imageuploader.service.UserService;
import com.luv2code.imageuploader.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Created by lzugaj on Wednesday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/package/option")
public class PackageController {

	private final PackageService packageService;

	private final UserService userService;

	private final ImageFormatService imageFormatService;

	public PackageController(PackageService packageService, UserService userService, ImageFormatService imageFormatService) {
		this.packageService = packageService;
		this.userService = userService;
		this.imageFormatService = imageFormatService;
	}

	@GetMapping
	private String showUserPackageOptionForm(Model model, Principal principal) {
		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());
		if (user.getUserPackage() != null) {
			log.info("User `{}` already has a Package.", user.getUserName());
			return "redirect:/home";
		}

		List<Package> packages = packageService.findAll();
		model.addAttribute("packages", packages);
		log.info("Successfully fetched all Packages.");

		String freeExtensions = getListOfExtensions(Utils.ID_FREE);
		String proExtensions = getListOfExtensions(Utils.ID_PRO);
		String goldExtensions = getListOfExtensions(Utils.ID_GOLD);

		model.addAttribute("freeExtensions", freeExtensions);
		model.addAttribute("proExtensions", proExtensions);
		model.addAttribute("goldExtensions", goldExtensions);

		return "user-package/user-package-form";
	}

	private String getListOfExtensions(Long packageId) {
		List<String> imageFormats = imageFormatService.findAllForPackage(packageId);
		return imageFormats.toString();
	}

	@GetMapping("/{packageId}")
	private String saveUserPackageOptionForm(@PathVariable Long packageId, Principal principal, Model model) {
		String username = principal.getName();
		log.info("Saving Package option for User with username: `{}`.", username);

		User user = userService.choosePackageOption(packageId, principal.getName());
		log.info("Successfully saved Package option with id `{}` to User with username `{}`.", packageId, username);
		model.addAttribute("user", user);

		return "redirect:/home";
	}
}
