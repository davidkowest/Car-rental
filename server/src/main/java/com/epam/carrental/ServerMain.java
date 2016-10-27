package com.epam.carrental;

import com.epam.carrental.data_generator.DataGeneratorOnStartup;
import com.epam.carrental.config.HttpServerConfig;
import com.epam.carrental.config.ServicesConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;


public class ServerMain {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.child(HttpServerConfig.class)
                .parent(ServicesConfig.class)
                .run(args);
    }
}
