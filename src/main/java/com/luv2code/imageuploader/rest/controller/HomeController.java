package com.luv2code.imageuploader.rest.controller;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class HomeController {

    private final UserServiceImpl userService;

    public HomeController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String indexPage(Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        log.info("Successfully founded User with username: `{}`.", user.getUserName());

        model.addAttribute("user", user);
        return "home/index";
    }
}
