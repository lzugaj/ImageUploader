package com.luv2code.imageuploader.rest.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.imageuploader.entity.ImageFormat;
import com.luv2code.imageuploader.entity.ImageSize;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.ImageFormatService;
import com.luv2code.imageuploader.service.ImageSizeService;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user-post")
public class PostController {

	private final PostService postService;

	private final ImageFormatService imageFormatService;

	private final ImageSizeService imageSizeService;

	private final UserService userService;

	@Autowired
	public PostController(PostService postService, ImageFormatService imageFormatService,
			ImageSizeService imageSizeService, UserService userService) {
		this.postService = postService;
		this.imageFormatService = imageFormatService;
		this.imageSizeService = imageSizeService;
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@GetMapping("/create/form")
	public String showUserPostForm(Model model) {
		model.addAttribute("post", new Post());

		List<ImageFormat> imageFormats = getAllImageFormats();
		model.addAttribute("imageFormats", imageFormats);

		List<ImageSize> imageSizes = getAllImageSize();
		model.addAttribute("imageSizes", imageSizes);

		return "user-post/post-form";
	}

	@PostMapping("/submit/form")
	public String saveUserPostForm(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult,
			Model model, Principal principal, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<ImageFormat> imageFormats = getAllImageFormats();
			model.addAttribute("imageFormats", imageFormats);

			List<ImageSize> imageSizes = getAllImageSize();
			model.addAttribute("imageSizes", imageSizes);

			model.addAttribute("creatingPostError", "Please fill all required fields.");
			return "user-post/post-form";
		}

		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());

		postService.save(post, user);
		redirectAttributes.addFlashAttribute("postSuccessMessage", "You have successfully added new post!");
		return "redirect:/home";
	}

	private List<ImageFormat> getAllImageFormats() {
		List<ImageFormat> imageFormats = imageFormatService.findAll();
		log.info("Successfully fetched all Image formats.");
		return imageFormats;
	}

	private List<ImageSize> getAllImageSize() {
		List<ImageSize> imageSizes = imageSizeService.findAll();
		log.info("Successfully fetched all Image sizes.");
		return imageSizes;
	}
}
