package com.luv2code.imageuploader.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        log.info("In CustomAuthenticationSuccessHandler");
        String userName = authentication.getName();
        log.info("userName: `{}`", userName);

        User theUser = userService.findByUserName(userName);
        HttpSession session = request.getSession();

        session.setAttribute("user", theUser);
        response.sendRedirect(request.getContextPath() + "/user/package/option");
    }
}
