package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lzugaj on Saturday, January 2020
 */

@Slf4j
@Controller
@RequestMapping("/download/image")
public class DownloadImageController {

	private final PostService postService;

	@Autowired
	public DownloadImageController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/{id}")
	public String showDownloadImageForm(@PathVariable Long id, Model model) {
		Post post = postService.findById(id);
		log.info("Successfully founded Post with id: `{}`", id);

		model.addAttribute("post", post);
		return "download/download-form";
	}
}
