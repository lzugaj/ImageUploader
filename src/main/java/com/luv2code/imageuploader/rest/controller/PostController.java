package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.service.impl.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Controller
@RequestMapping("/user-post")
public class PostController {

	private final PostServiceImpl postService;

	@Autowired
	public PostController(PostServiceImpl postService) {
		this.postService = postService;
	}

	@GetMapping("/create/form")
	public String showUserPostForm() {
		return "user-post/post-form";
	}
}
