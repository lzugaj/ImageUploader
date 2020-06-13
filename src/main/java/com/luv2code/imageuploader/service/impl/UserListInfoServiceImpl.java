package com.luv2code.imageuploader.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.entity.Comment;
import com.luv2code.imageuploader.entity.Post;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.CommentRepository;
import com.luv2code.imageuploader.repository.PostRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.UserListInfoService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Sunday, January 2020
 */

@Slf4j
@Service
public class UserListInfoServiceImpl implements UserListInfoService {

	private UserRepository userRepository;

	private PostRepository postRepository;

	private CommentRepository commentRepository;

	@Autowired
	public UserListInfoServiceImpl(UserRepository userRepository, PostRepository postRepository,
			CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		log.info("Finding all Users.");
//		List<User> searchedUsers = new ArrayList<>();
//		for (User user : users) {
//			if (!user.getUserName().equals("admin")) {
//				searchedUsers.add(user);
//			}
//		}
//
//		log.info("Finding all Users except Admin.");
//		return searchedUsers;

		return users.stream()
				.filter(user -> !user.getUserName().equals("admin"))
				.collect(Collectors.toList());
	}

	@Override
	public int getNumberOfPostsForUser(String username) {
		User searchedUser = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		List<Post> posts = postRepository.findAllByUserId(searchedUser.getId());
		log.info("Successfully founded all Posts for User with username: `{}`", username);

		int numberOfPostsForUser = posts.size();
		log.info("User, `{}`, has total of `{}` posts.", username, numberOfPostsForUser);
		return numberOfPostsForUser;
	}

	@Override
	public int getNumberOfCommentsForUser(String username) {
		User searchedUser = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		List<Comment> comments = commentRepository.findAllByUserId(searchedUser.getId());
		log.info("Successfully founded all Comments for User with username: `{}`", username);

		int numberOfCommentsForUser = comments.size();
		log.info("User, `{}`, has total of `{}` comments.", username, numberOfCommentsForUser);
		return numberOfCommentsForUser;
	}

	@Override
	public int getNumberOfSavedImagesForUser(String username) {
		User user = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		// TODO: Fali mi još jedna kolona u tablici user -> numberOfSavedImages;
		return 0;
	}

	@Override
	public int getNumberOfDownloadImagesForUser(String username) {
		User user = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		int numberOfDownloadedImages = user.getDownloadImages().size();
		log.info("User, `{}`, has total of `{}` downloaded images.", username, numberOfDownloadedImages);
		return numberOfDownloadedImages;
	}

	@Override
	public int getNumberOfUploadedImagesWithCurrentPackageForUser(String username) {
		User user = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		int numberOfUploadedImagesWithCurrentPackage = user.getUploadedImagesWithCurrentPackage();
		log.info("User, `{}`, has total of `{}` uploaded images with current package.", username, numberOfUploadedImagesWithCurrentPackage);
		return numberOfUploadedImagesWithCurrentPackage;
	}

	@Override
	public long getNumberOfUploadedImageSizeWithCurrentPackageForUser(String username) {
		User user = userRepository.findByUserName(username);
		log.info("Successfully founded User with username: `{}`", username);

		long numberOfUploadedImageSizeWithCurrentPackage = user.getUploadedImageSizeWithCurrentPackage();
		log.info("User, `{}`, has total of `{}` MB uploaded image size with current package.", username, numberOfUploadedImageSizeWithCurrentPackage);
		return numberOfUploadedImageSizeWithCurrentPackage;
	}

	@Override
	public User delete(Long id) {
		User searchedUser = userRepository.findById(id).orElse(null);
		log.info("Successfully founded User with id: `{}`", id);

		userRepository.deleteById(id);
		log.info("Delete User with id: `{}`", id);
		return searchedUser;
	}
}
