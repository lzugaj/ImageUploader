package com.luv2code.imageuploader.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        log.info("Uploading Login Form");
        return "login/login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
