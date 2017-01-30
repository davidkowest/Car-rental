package com.epam.carrental.jms;


import com.epam.carrental.dto.CustomerDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class DataChangedInterceptor {

    @Autowired
    MessageSender messageSender;

    @AfterReturning("@annotation(DataChangedAspect)")
    public void sendDataChangedNotification(JoinPoint joinPoint) throws Throwable {
        Boolean contextRefreshed=Boolean.valueOf(System.getProperty("context.started"));
        if (contextRefreshed) {
            Object[] args = joinPoint.getArgs();
            messageSender.sendNotification(new DataChangedMessage((CustomerDTO)args[0]));
        }
    }
}