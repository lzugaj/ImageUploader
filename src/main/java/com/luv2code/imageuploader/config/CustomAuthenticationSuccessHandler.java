package com.luv2code.imageuploader.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.luv2code.imageuploader.entity.User;
import com.luv2code.imageuploader.service.UserService;

/**
 * Created by lzugaj on Monday, November 2019
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        System.out.println("\n--------------- In CustomAuthenticationSuccessHandler ---------------");
        String userName = authentication.getName();
        System.out.println("userName = " + userName);

        User theUser = userService.findByUserName(userName);
        HttpSession session = request.getSession();

        session.setAttribute("user", theUser);
        response.sendRedirect(request.getContextPath());
    }
}
