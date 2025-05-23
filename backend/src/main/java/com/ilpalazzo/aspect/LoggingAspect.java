package com.ilpalazzo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    //private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoggingAspect.class); //for disabling red line under log (it annoyed me) // use in your version no need to have 2 loggers


    @Before("execution(* com.ilpalazzo.controller.OrderController.*(..))")
    public void doLogBefore(JoinPoint joinPoint) {
        log.info("ActionLog: {}.{} method call started.",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    @After("execution(* com.ilpalazzo.controller.OrderController.*(..))")
    public void doLogAfter(JoinPoint joinPoint) {
        log.info("ActionLog: {}.{} method call finished.",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    @Around("@annotation(com.ilpalazzo.annotation.TimeTracker)")
    public Object doLogMeasureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object returnValue = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("ActionLog: Execution time by {}.{} method is {} ms.",
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(),
                (endTime - startTime));

        return returnValue;
    }
}
