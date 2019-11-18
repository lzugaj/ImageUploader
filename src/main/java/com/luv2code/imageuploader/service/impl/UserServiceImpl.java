package com.luv2code.imageuploader.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.imageuploader.dto.UserDto;
import com.luv2code.imageuploader.entity.Role;
import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.repository.RoleRepository;
import com.luv2code.imageuploader.repository.UserRepository;
import com.luv2code.imageuploader.service.UserService;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    @Transactional
    public User save(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findRoleByName("ROLE_USER")));

        User newUser = userRepository.save(user);
        LOGGER.info("Creating new User with username: ´{}´", userDto.getUserName());
        return newUser;
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
}
