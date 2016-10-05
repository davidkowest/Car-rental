package com.epam.carrental;

import com.epam.carrental.config.ClientConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {

    public static void main(String[] args) {
       new AnnotationConfigApplicationContext(ClientConfig.class);
    }
}
