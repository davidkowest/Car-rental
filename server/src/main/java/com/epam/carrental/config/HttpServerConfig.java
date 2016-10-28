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
    SimpleHttpInvokerServiceExporter rentReturnServiceExporter;

    @Autowired
    SimpleHttpInvokerServiceExporter currentRentalsServiceExporter;

    @Autowired
    SimpleHttpInvokerServiceExporter rentalsHistoryServiceExporter;

    @Value("${remote.port}")
    private String port;

    @Value("${remote.service.info}")
    private String serviceInfoPath;

    @Value("${remote.service.car}")
    private String carServicePath;

    @Value("${remote.service.customer}")
    private String customerServicePath;

    @Value("${remote.service.rentReturnCar}")
    private String rentReturnServicePath;

    @Value("${remote.service.currentRentals}")
    private String currentRentalsServicePath;

    @Value("${remote.service.rentalHistory}")
    private String rentalHistoryServicePath;

    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/" + serviceInfoPath, serverInfoServiceExporter);
        contexts.put("/" + carServicePath, carServiceExporter);
        contexts.put("/" + customerServicePath, customerServiceExporter);
        contexts.put("/" + rentReturnServicePath, rentReturnServiceExporter);
        contexts.put("/" + currentRentalsServicePath, currentRentalsServiceExporter);
        contexts.put("/" + rentalHistoryServicePath, rentalsHistoryServiceExporter);
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        serverFactory.setExecutor(Executors.newFixedThreadPool(4));
        return serverFactory;
    }


}
