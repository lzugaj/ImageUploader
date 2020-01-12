package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PackageService;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;
import com.luv2code.imageuploader.utils.MessageError;
import com.luv2code.imageuploader.utils.MessageSuccess;
import com.luv2code.imageuploader.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/post")
public class PostController {

	private final PostService postService;

	private final UserService userService;

	private final PackageService packageService;

	@Autowired
	public PostController(PostService postService, UserService userService, PackageService packageService) {
		this.postService = postService;
		this.userService = userService;
		this.packageService = packageService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/create/form")
	public String showUserPostForm(Model model) {
		model.addAttribute("post", new Post());
		return "user-post/post-form";
	}

	@PostMapping("/submit/form")
	public String saveUserPostForm(@RequestParam("postImage") MultipartFile file,
								   @RequestParam("postDescription") String description,
								   @RequestParam("postHashTags") String hashTags,
								   Principal principal, RedirectAttributes redirectAttributes) throws IOException {
		if (file.isEmpty() || description.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", MessageError.PLEASE_FILL_ALL_REQUIRED_FIELDS);
			return "redirect:/user/post/create/form";
		}

		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());

		String imageExtension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
		switch (user.getUserPackage().getName()) {
			case Utils.FREE:
				if (isCurrentNumberOfUploadedImageGreaterThanPackageImageSize(user, Utils.ID_FREE)
						|| !isImageFileContainsExtensionForCurrentPackage(imageExtension, Utils.ID_FREE)) {
					redirectAttributes.addFlashAttribute("limitError", MessageError.PACKAGE_TRACKER_VALIDATION);
					return "redirect:/user/post/create/form";
				} else {
					Post post = postService.save(user, file, description, hashTags);
					log.info("Successfully saved new Post with id: ´{}´, for User with id: `{}`", post.getId(), user.getId());
				}

				break;
			case Utils.PRO:
				if (isCurrentNumberOfUploadedImageGreaterThanPackageImageSize(user, Utils.ID_PRO)
						|| !isImageFileContainsExtensionForCurrentPackage(imageExtension, Utils.ID_PRO)) {
					redirectAttributes.addFlashAttribute("limitError", MessageError.PACKAGE_TRACKER_VALIDATION);
					return "redirect:/user/post/create/form";
				} else {
					Post post = postService.save(user, file, description, hashTags);
					log.info("Successfully saved new Post with id: ´{}´, for User with id: `{}`", post.getId(), user.getId());
				}

				break;
			case Utils.GOLD:
				if (isCurrentNumberOfUploadedImageGreaterThanPackageImageSize(user, Utils.ID_GOLD)
						|| !isImageFileContainsExtensionForCurrentPackage(imageExtension, Utils.ID_GOLD)) {
					redirectAttributes.addFlashAttribute("limitError", MessageError.PACKAGE_TRACKER_VALIDATION);
					return "redirect:/user/post/create/form";
				} else {
					Post post = postService.save(user, file, description, hashTags);
					log.info("Successfully saved new Post with id: ´{}´, for User with id: `{}`", post.getId(), user.getId());
				}

				break;
		}

		redirectAttributes.addFlashAttribute("postSuccessMessage", principal.getName() + MessageSuccess.SUCCESSFULLY_ADDED_POST);
		return "redirect:/home";
	}

	private boolean isCurrentNumberOfUploadedImageGreaterThanPackageImageSize(User user, Long packageId) {
		boolean result = false;
		if (user.getUploadedImagesWithCurrentPackage() >= packageService.findById(packageId).getDailyUploadLimit()) {
			result = true;
		}

		return result;
	}

	private boolean isImageFileContainsExtensionForCurrentPackage(String imageExtension, Long packageId) {
		boolean result = false;
		Package searchedPackage = packageService.findById(packageId);
		if (searchedPackage.getName().equals(Utils.FREE)) {
			for (String extension : Utils.FREE_EXTENSION_LIST) {
				if (extension.equals(imageExtension)) {
					result = true;
					break;
				}
			}
		} else if (searchedPackage.getName().equals(Utils.PRO)) {
			for (String extension : Utils.PRO_EXTENSION_LIST) {
				if (extension.equals(imageExtension)) {
					result = true;
					break;
				}
			}
		} else {
			for (String extension : Utils.GOLD_EXTENSION_LIST) {
				if (extension.equals(imageExtension)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}
}
