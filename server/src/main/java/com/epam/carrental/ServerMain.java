package com.epam.carrental;

import com.epam.carrental.config.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServerMain {

    public static void main(String[] args) {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);


    }
}
