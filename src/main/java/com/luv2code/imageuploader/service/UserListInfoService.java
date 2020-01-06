package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Created by lzugaj on Sunday, January 2020
 */

public interface UserListInfoService {

	List<User> findAll();

	int getNumberOfPostsForUser(String username);

	int getNumberOfCommentsForUser(String username);

	int getNumberOfSavedImagesForUser(String username);

	int getNumberOfDownloadImagesForUser(String username);

	int getNumberOfUploadedImagesWithCurrentPackageForUser(String username);

	long getNumberOfUploadedImageSizeWithCurrentPackageForUser(String username);

	Optional<User> delete(Long id);

}
