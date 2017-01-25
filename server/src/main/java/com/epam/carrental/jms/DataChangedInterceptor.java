package com.epam.carrental.jms;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@Slf4j
public class DataChangedInterceptor {

    @Autowired
    MessageSender messageSender;

    @AfterReturning("@annotation(DataChangedAspect)")
    public void sendDataChangedNotification() throws Throwable {
        Boolean contextRefreshed=Boolean.valueOf(System.getProperty("context.started"));
        if (contextRefreshed) {
            messageSender.sendNotification(new DataChangedMessage());
        }
    }
}