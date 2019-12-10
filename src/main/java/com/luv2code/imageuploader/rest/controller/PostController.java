package com.luv2code.imageuploader.rest.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.PostService;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user/post")
public class PostController {

	private final PostService postService;

	private final UserService userService;

	@Autowired
	public PostController(PostService postService, UserService userService) {
		this.postService = postService;
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
		return "user-post/post-form";
	}

	@PostMapping("/submit/form")
	public String saveUserPostForm(@RequestParam("postImage") MultipartFile file,
								   @RequestParam("postDescription") String description,
								   @RequestParam("postHashTags") String hashTags,
								   Principal principal, RedirectAttributes redirectAttributes) throws IOException {
		if (file.isEmpty() || description.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please fill all fields.");
			return "redirect:/user/post/create/form";
		}

		User user = userService.findByUserName(principal.getName());
		log.info("Successfully founded User with username: `{}`", user.getUserName());

		Post post = postService.save(user, file, description, hashTags);
		log.info("Successfully saved new Post with id: ´{}´, for User with id: `{}`", post.getId(), user.getId());
		redirectAttributes.addFlashAttribute("postSuccessMessage", "You have successfully added new post!");
		return "redirect:/home";
	}
}
