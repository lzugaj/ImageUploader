package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public Post save(Post post) {
		return null;
	}
}
