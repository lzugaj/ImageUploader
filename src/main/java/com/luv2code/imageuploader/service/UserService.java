package com.luv2code.imageuploader.service;

import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

/**
 * Created by lzugaj on Monday, November 2019
 */

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    List<User> findAll();

    List<User> findAllByPackageName(Package searchedPackage);

    User save(UserDto userDto);

    User choosePackageOption(Long packageId, String username);

    Map<Long, String> mapAllProfileImages(List<User> users);

}
