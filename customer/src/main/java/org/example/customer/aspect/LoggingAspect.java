package org.example.customer.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* org.example.customer.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Incoming request: " + joinPoint.getSignature().getName());
    }
}
