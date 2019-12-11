package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.entity.UserProfile;
import com.luv2code.imageuploader.repository.UserProfileRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserRepository userRepository;

	private final UserProfileRepository userProfileRepository;

	@Autowired
	public UserProfileServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository) {
		this.userRepository = userRepository;
		this.userProfileRepository = userProfileRepository;
	}

	@Override
	public User findUserProfileByUsername(String userName) {
		User user = userRepository.findByUsername(userName);
		log.info("Finding User with username: `{}`", userName);
		return user;
	}

	@Override
	public UserProfile save(User updatedUser, MultipartFile userProfileImage) throws IOException {
		User user = userRepository.findById(updatedUser.getId()).orElse(null);
		log.info("Getting User with id: `{}`.", updatedUser.getId());

		String imageFileName = StringUtils.cleanPath(userProfileImage.getOriginalFilename());
		log.info("Successfully get image file name: `{}`.", imageFileName);

		UserProfile updatedUserProfile = userProfileRepository.findById(updatedUser.getId()).orElse(null);
		if (updatedUserProfile != null) {
			updatedUserProfile.setProfileImage(userProfileImage.getBytes());
		}

		userRepository.save(user);
		return updatedUserProfile;
	}
}
