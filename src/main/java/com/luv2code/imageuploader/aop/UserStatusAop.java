package com.luv2code.imageuploader.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, April 2020
 */

@Slf4j
@Aspect
@Component
public class UserStatusAop {

    @AfterReturning(
            pointcut = "execution(* com.luv2code.imageuploader.service.impl.UserServiceImpl.findByUserName(..))",
            returning= "result")
    public void printUserInfoAfterLogin(JoinPoint joinPoint, Object result) {
        System.out.println("Method name: " + joinPoint.getSignature().getName());
        System.out.println("Logged in user is: " + result);
    }

    @AfterReturning(
            pointcut = "execution(* com.luv2code.imageuploader.service.impl.UserProfileServiceImpl.findUserProfileByUsername(..))",
            returning= "result")
    public void printUserProfileInfoAfterClick(JoinPoint joinPoint, Object result) {
        System.out.println("Method name: " + joinPoint.getSignature().getName());
        System.out.println("Logged in user is: " + result);
    }

    @AfterReturning(
            pointcut = "execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findById(..))",
            returning= "result")
    public void printProfileInfoAfterClick(JoinPoint joinPoint, Object result) {
        System.out.println("Method name: " + joinPoint.getSignature().getName());
        System.out.println("Logged in user is: " + result);
    }

    @AfterReturning(
            pointcut = "execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.delete(..))",
            returning= "result")
    public void printPostInfoAfterDelete(JoinPoint joinPoint, Object result) {
        System.out.println("Method name: " + joinPoint.getSignature().getName());
        System.out.println("Logged in user is: " + result);
    }
}
