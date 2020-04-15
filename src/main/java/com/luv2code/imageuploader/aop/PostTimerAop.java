package com.luv2code.imageuploader.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Monday, April 2020
 */

@Slf4j
@Aspect
@Component
public class PostTimerAop {

    @Around("@annotation(com.luv2code.imageuploader.aop.TrackExecutionTime)")
    public Object measureTimeNeededForSavingPost(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object object = proceedingJoinPoint.proceed();
        long entTime = System.nanoTime();
        long result = entTime - startTime;

        log.info("It took " + result + " nano seconds to execute method: " + proceedingJoinPoint.getSignature());
        return object;
    }

}
