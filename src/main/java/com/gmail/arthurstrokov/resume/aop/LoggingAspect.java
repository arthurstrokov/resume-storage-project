package com.gmail.arthurstrokov.resume.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging aspect
 * logging by levels: service, controller, repository
 *
 * @author Arthur Strokov
 */
@Aspect
public class LoggingAspect {
    private Logger log;

    public LoggingAspect(String loggerName) {
        super();
        log = LoggerFactory.getLogger(loggerName);
    }

    /**
     * Create pointcut
     * Describe witch packages should be logged.
     */
    @Pointcut("execution(* com.gmail.arthurstrokov.resume.service.EmployeeService.*(..)) ||" +
            "execution(* com.gmail.arthurstrokov.resume.controller.EmployeeController.*(..)) ||" +
            "execution(* com.gmail.arthurstrokov.resume.repository.EmployeeRepository.*(..))"
    )
    public void executeLogging() {
    }

    /**
     * Log exceptions
     * Works when an exception is thrown
     *
     * @param joinPoint joinPoint
     * @param exception Throwable
     * @see JoinPoint
     * @see Throwable
     */
    @AfterThrowing(pointcut = "executeLogging()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in '{}' Method: '{}' Message: '{}'",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }

    /**
     * Log incoming args
     * Works before calling methods
     *
     * @param joinPoint joinPoint
     */
    @Before("executeLogging()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        log.info("Before: '{}' Method: '{}' Args: '{}'",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    /**
     * Log returning values
     * Works after calling methods
     *
     * @param joinPoint joinPoint
     * @param result    result
     */
    @AfterReturning(pointcut = "executeLogging()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
        log.info("After: '{}' Method: '{}' ->",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
        if (result != null) {
            log.info("-> return value : '{}'", result);
        } else {
            log.info("-> with null as return value.");
        }
    }
}
