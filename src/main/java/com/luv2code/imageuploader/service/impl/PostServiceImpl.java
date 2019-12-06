package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
	public List<Post> findAll() {
		List<Post> posts = postRepository.findAll();
		log.info("Getting all Posts.");
		return posts;
	}

	@Override
	public Post save(User user, MultipartFile postImage, String postDescription) throws IOException {
		Post newPost = new Post();
		User postCreator = userRepository.findById(user.getId()).orElse(null);
		log.info("Getting User with id: `{}`.", user.getId());
		String imageFileName = StringUtils.cleanPath(postImage.getOriginalFilename());
		log.info("Successfully get image file name: `{}`.", imageFileName);

		newPost.setUser(postCreator);
		newPost.setDescription(postDescription);
		newPost.setPostImage(postImage.getBytes());
		newPost.setNumberOfLikes(0);
		newPost.setNumberOfDownloads(0);
		newPost.setDateOfPost(LocalDateTime.now());
		newPost.setComments(null);
		newPost.setDownloadImages(null);
		log.info("Setting Post attributes.");

		newPost = postRepository.save(newPost);
		log.info("Saving new Post with id: `{}`.", newPost.getId());
		return newPost;
	}
}
