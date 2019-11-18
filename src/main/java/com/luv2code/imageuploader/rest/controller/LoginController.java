package com.luv2code.imageuploader.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/login")
    public String showLoginForm() {
        LOGGER.info("Uploading Login Form");
        return "login/login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
