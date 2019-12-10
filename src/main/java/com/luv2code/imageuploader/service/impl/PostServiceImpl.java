package com.luv2code.imageuploader.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
	public List<Post> findAll() {
		List<Post> posts = postRepository.findAll();
		log.info("Getting all Posts.");
		return posts;
	}

	@Override
	public List<Post> findAllForUser(User user) {
		List<Post> posts = findAll();
		List<Post> userPosts = new ArrayList<>();
		log.info("Getting all Posts for User with username: `{}`", user.getUserName());
		for (Post post : posts) {
			if (post.getUser().getUserName().equals(user.getUserName())) {
				userPosts.add(post);
			}
		}

		return userPosts;
	}

	@Override
	public Post save(User user, MultipartFile postImage, String postDescription, String postHashTags) throws IOException {
		Post newPost = new Post();
		User postCreator = userRepository.findById(user.getId()).orElse(null);
		log.info("Getting User with id: `{}`.", user.getId());
		String imageFileName = StringUtils.cleanPath(postImage.getOriginalFilename());
		log.info("Successfully get image file name: `{}`.", imageFileName);

		newPost.setUser(postCreator);
		newPost.setDescription(postDescription);
		newPost.setPostImage(postImage.getBytes());

		String generateHashTagsFormat = generateHastTagsFormat(postHashTags);
		newPost.setHashTag(generateHashTagsFormat);

		newPost.setNumberOfLikes(0);
		newPost.setNumberOfDownloads(0);
		newPost.setImageFileSize(postImage.getSize());
		newPost.setDateOfPost(LocalDateTime.now());
		newPost.setComments(null);
		newPost.setDownloadImages(null);
		log.info("Setting Post attributes.");

		newPost = postRepository.save(newPost);
		log.info("Saving new Post with id: `{}`.", newPost.getId());
		return newPost;
	}

	private String generateHastTagsFormat(String postHashTags) {
		String[] hashTags = postHashTags.split("\\s+");
		List<String> newHashTags = new ArrayList<>();
		for (String hashTag : hashTags) {
			hashTag = "#" + hashTag;
			newHashTags.add(hashTag);
		}

		return String.valueOf(newHashTags);
	}
}
