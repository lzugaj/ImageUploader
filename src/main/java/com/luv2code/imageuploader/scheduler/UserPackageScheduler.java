package com.luv2code.imageuploader.scheduler;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by lzugaj on Friday, November 2019
 */

@Slf4j
@Component
public class UserPackageScheduler {

	private final UserServiceImpl userService;

	private final UserRepository userRepository;

	@Autowired
	public UserPackageScheduler(UserServiceImpl userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	// TODO: Later this: @Scheduled(cron = "0 0/5 * * * ?")
	@Scheduled(cron = "0 0 12 * * ?")
	public void resetPackagesOptionForAllUsersAfter12PM() {
		List<User> users = userService.findAll();
		for (User user : users) {
			user.setUserPackage(null);
			user.setUploadedImagesWithCurrentPackage(0);
			user.setUploadedImageSizeWithCurrentPackage(0L);
			log.info("Reset Package for User with username: `{}`.", user.getUserName());
			userRepository.save(user);
			log.info("Successfully reset Package for User with username: `{}`.", user.getUserName());
		}
	}
}
