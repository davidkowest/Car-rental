package com.epam.carrental.performance;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class PerformanceMonitorInterceptor {

    @Around("@annotation(PerformanceMonitor)")
    public Object measureTimeExecution(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object object = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        StringBuilder messageArgsBuilder = new StringBuilder();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            messageArgsBuilder.append(arg.toString()).append(" ");
        }
        String messageArgs = messageArgsBuilder.toString();

        log.info("Method: " + pjp.getSignature().getDeclaringTypeName() +
                "." + pjp.getSignature().getName() +
                " args: " + messageArgs +
                " time: " + elapsedTime + " milli.");
        return object;
    }
}