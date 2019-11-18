package com.luv2code.imageuploader.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.User;

/**
 * Created by lzugaj on Monday, November 2019
 */

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    User save(UserDto userDto);

}
