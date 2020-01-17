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
import java.util.Objects;

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
		User user = userRepository.findByUserName(userName);
		log.info("Finding User with username: `{}`", userName);
		return user;
	}

	@Override
	public UserProfile save(String username, User updatedUser, MultipartFile userProfileImage) throws IOException {
		User user = userRepository.findByUserName(username);
		log.info("Getting User with id: `{}`.", updatedUser.getId());

		String imageFileName = StringUtils.cleanPath(Objects.requireNonNull(userProfileImage.getOriginalFilename()));
		log.info("Successfully get image file name: `{}`.", imageFileName);

		UserProfile updatedUserProfile = userProfileRepository.findById(user.getId()).orElse(null);
		if (updatedUserProfile != null) {
			if (userProfileImage.getBytes().length > 0) {
				updatedUserProfile.setProfileImage(userProfileImage.getBytes());
			}

			updatedUserProfile.setUserDescription(updatedUser.getUserProfile().getUserDescription());
			updatedUserProfile.setCountryName(updatedUser.getUserProfile().getCountryName());
			updatedUserProfile.setCityName(updatedUser.getUserProfile().getCityName());
			updatedUserProfile.setPositionName(updatedUser.getUserProfile().getPositionName());
			updatedUserProfile.setCompanyName(updatedUser.getUserProfile().getCompanyName());
			updatedUserProfile.setStartYear(updatedUser.getUserProfile().getStartYear());
			updatedUserProfile.setEndYear(updatedUser.getUserProfile().getEndYear());
			updatedUserProfile.setIsWorkingHere(updatedUser.getUserProfile().getIsWorkingHere());
			updatedUserProfile.setSchoolName(updatedUser.getUserProfile().getSchoolName());
			updatedUserProfile.setConcentrationName(updatedUser.getUserProfile().getConcentrationName());
			updatedUserProfile.setSecondaryConcentrationName(updatedUser.getUserProfile().getSecondaryConcentrationName());
			updatedUserProfile.setDegreeTypeName(updatedUser.getUserProfile().getDegreeTypeName());
			updatedUserProfile.setGraduationYear(updatedUser.getUserProfile().getGraduationYear());
		}

		userRepository.save(user);
		return updatedUserProfile;
	}
}
