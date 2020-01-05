package com.luv2code.imageuploader.service.impl;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.UserListInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzugaj on Sunday, January 2020
 */

@Slf4j
@Service
public class UserListInfoServiceImpl implements UserListInfoService {

	private UserRepository userRepository;

	@Autowired
	public UserListInfoServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		log.info("Finding all Users.");
		List<User> searchedUsers = new ArrayList<>();
		for (User user : users) {
			if (!user.getUserName().equals("admin")) {
				searchedUsers.add(user);
			}
		}

		log.info("Finding all Users except Admin.");
		return searchedUsers;
	}
}
