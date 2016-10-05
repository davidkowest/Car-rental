package com.epam.carrental;

import com.epam.carrental.config.ServerConfig;
import com.epam.carrental.services.ServerInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ServerMain {


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServerConfig.class);
        ServerInfo serverInfo = context.getBean(ServerInfo.class);

        System.out.println(serverInfo.getServerInfo());


    }
}
