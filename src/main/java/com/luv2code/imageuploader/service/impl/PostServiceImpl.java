package com.luv2code.imageuploader.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.PostService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Sunday, November 2019
 */

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserRepository userRepository;

	@Autowired
	public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Post save(Post post, User user) {
		Post newPost = new Post();
		newPost.setDescription(post.getDescription());
		newPost.setPostImage(post.getPostImage());
		newPost.setImageSize(post.getImageSize());
		newPost.setImageFormat(post.getImageFormat());
		newPost.setHashTags(post.getHashTags());

		User postCreator = userRepository.findById(user.getId()).orElse(null);
		newPost.setUser(postCreator);

		newPost.setNumberOfLikes(0);
		newPost.setNumberOfDownloads(0);
		newPost.setDateOfPost(LocalDateTime.now());
		newPost.setComments(null);
		newPost.setDownloadImages(null);

		newPost = postRepository.save(newPost);
		return newPost;
	}
}
