package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.entity.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by lzugaj on Tuesday, December 2019
 */

public interface UserProfileService {

	User findUserProfileByUsername(String userName);

	UserProfile save(User updatedUser, MultipartFile userProfileImage) throws IOException;

}
