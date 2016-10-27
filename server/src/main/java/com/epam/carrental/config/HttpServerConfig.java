package com.epam.carrental.config;


import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@Configuration
public class HttpServerConfig {

    @Autowired
    SimpleHttpInvokerServiceExporter serverInfoServiceExporter;

    @Autowired
    SimpleHttpInvokerServiceExporter carServiceExporter;

    @Autowired
    SimpleHttpInvokerServiceExporter customerServiceExporter;

    @Autowired
    SimpleHttpInvokerServiceExporter rentedCarServiceExporter;

    @Value("${remote.service.info}")
    private String serviceInfoPath;

    @Value("${remote.service.car}")
    private String carServicePath;

    @Value("${remote.service.customer}")
    private String customerServicePath;

    @Value("${remote.port}")
    private String port;

    @Value("${remote.service.rentedCar}")
    private String rentedCarServicePath;

    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/" + serviceInfoPath, serverInfoServiceExporter);
        contexts.put("/" + carServicePath, carServiceExporter);
        contexts.put("/" + customerServicePath, customerServiceExporter);
        contexts.put("/" + rentedCarServicePath, rentedCarServiceExporter);
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        serverFactory.setExecutor(Executors.newFixedThreadPool(4));
        return serverFactory;
    }


}
