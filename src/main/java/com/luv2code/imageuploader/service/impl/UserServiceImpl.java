package com.luv2code.imageuploader.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.aop.TrackExecutionTime;
import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.Package;
import com.luv2code.imageuploader.entity.Role;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.entity.UserProfile;
import com.luv2code.imageuploader.repository.RoleRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PackageServiceImpl packageService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            PackageServiceImpl packageService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.packageService = packageService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        log.info("Getting all Users.");
        return users;
    }

    @Override
    public List<User> findAllByPackageName(Package searchedPackage) {
//        List<User> usersWithSamePackageName = new ArrayList<>();
//        List<User> users = findAll();
//        for (User user : users) {
//            if (user.getUserPackage() != null) {
//                if (user.getUserPackage().getName().equals(searchedPackage.getName())) {
//                    usersWithSamePackageName.add(user);
//                }
//            }
//        }

//        return usersWithSamePackageName;

        return findAll().stream()
                .filter(user -> user.getUserPackage() != null)
                .filter(user -> user.getUserPackage().getName().equals(searchedPackage.getName()))
                .collect(Collectors.toList());
    }

    @TrackExecutionTime
    @Override
    @Transactional
    public User save(UserDto userDto) {
        User user = setVariablesBeforeSave(userDto);
        User newUser = userRepository.save(user);
        log.info("Creating new User with username: ´{}´", userDto.getUserName());
        return newUser;
    }

	private User setVariablesBeforeSave(UserDto userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setRoles(Collections.singletonList(roleRepository.findRoleByName("ROLE_USER")));
		user.setUploadedImagesWithCurrentPackage(0);
		user.setUploadedImageSizeWithCurrentPackage(0L);
		user.setUserPackage(null);
		user.setUserProfile(null);
		UserProfile userProfile = new UserProfile();
		user.setUserProfile(userProfile);

		return user;
	}

	@Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(
                role -> new SimpleGrantedAuthority(
                        role.getName())).collect(Collectors.toList());
    }

    @Override
    public User choosePackageOption(Long packageId, String username) {
        User searchedUser = userRepository.findByUserName(username);
        log.info("Successfully founded User with username: `{}`.", username);

        Package searchedPackage = packageService.findById(packageId);
        log.info("Successfully founded Package with id: `{}`.", packageId);

        searchedUser.setUserPackage(searchedPackage);
        log.info("Setting UserPackage for User with username: `{}`.", username);
        userRepository.save(searchedUser);
        log.info("Saving Package `{}`, to User with username: `{}`.", searchedPackage.getName(), username);

        List<User> usersWithSamePackage = findAllByPackageName(searchedPackage);
        log.info("Successfully fetched all Users with same Package name: `{}`.", searchedPackage.getName());
        searchedPackage.setUsers(usersWithSamePackage);
        log.info("Setting User for Package with name: `{}`.", searchedPackage.getName());
        return searchedUser;
    }

	@Override
	public Map<Long, String> mapAllProfileImages(List<User> users) {
//		Map<Long, String> userProfileImages = new HashMap<>();
//		for (User user : users) {
//			if (user.getUserProfile().getProfileImage() != null) {
//				byte[] userProfileImage = Base64.getEncoder().encode(user.getUserProfile().getProfileImage());
//				String imageUrl = new String(userProfileImage, StandardCharsets.UTF_8);
//				userProfileImages.put(user.getId(), imageUrl);
//			}
//		}
//
//		return userProfileImages;

        Map<Long, String> userProfileImages = new HashMap<>();
        users.stream()
                .filter(user -> user.getUserProfile().getProfileImage() != null)
                .forEach(
                        user -> userProfileImages.put(user.getId(), new String(Base64.getEncoder().encode(user.getUserProfile().getProfileImage()), StandardCharsets.UTF_8))
                );

        return userProfileImages;
	}

	@Override
	public Map<Long, User> mapAllUsers(List<User> users) {
		Map<Long, User> usersMap = new HashMap<>();
//		for (User user : users) {
//			usersMap.put(user.getId(), user);
//		}
//
//		return usersMap;

        users.forEach(user -> usersMap.put(user.getId(), user));
        return usersMap;
	}
}
