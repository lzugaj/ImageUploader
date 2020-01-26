package com.luv2code.imageuploader.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by lzugaj on Friday, January 2020
 */

@Slf4j
@Aspect
@Component
public class PostStatusAop {

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findAll(..))")
	public void beforeFindAllPosts(JoinPoint joinPoint) {
		log.info("Finding all Posts: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findAll(..))")
	public void afterFindAllPosts(JoinPoint joinPoint) {
		log.info("Found all Post: `{}`", joinPoint.getSignature().getName());
	}

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findAllForUser(..))")
	public void beforeFindAllPostsForUser(JoinPoint joinPoint) {
		log.info("Finding all Posts for selected User: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findAllForUser(..))")
	public void afterFindAllPostsForUser(JoinPoint joinPoint) {
		log.info("Found all Post for selected User: `{}`", joinPoint.getSignature().getName());
	}

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findById(..))")
	public void beforeFindByIdPost(JoinPoint joinPoint) {
		log.info("Finding Post by id: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.findById(..))")
	public void afterFindByIdPost(JoinPoint joinPoint) {
		log.info("Found Post by id: `{}`", joinPoint.getSignature().getName());
	}

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.save(..))")
	public void beforeCreatePost(JoinPoint joinPoint) {
		log.info("Creating Post: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.save(..))")
	public void afterCreatePost(JoinPoint joinPoint) {
		log.info("Created Post: `{}`", joinPoint.getSignature().getName());
	}

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.delete(..))")
	public void beforeDeletePost(JoinPoint joinPoint) {
		log.info("Deleting Post: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.delete(..))")
	public void afterDeletePost(JoinPoint joinPoint) {
		log.info("Deleted Post: `{}`", joinPoint.getSignature().getName());
	}

	@Before("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.deleteAllUserPosts(..))")
	public void beforeDeleteAllUserPosts(JoinPoint joinPoint) {
		log.info("Deleting all User Posts: `{}`", joinPoint.getSignature().getName());
	}

	@After("execution(* com.luv2code.imageuploader.service.impl.PostServiceImpl.deleteAllUserPosts(..))")
	public void afterDeleteAllUserPosts(JoinPoint joinPoint) {
		log.info("Deleted all User Posts: `{}`", joinPoint.getSignature().getName());
	}

}
