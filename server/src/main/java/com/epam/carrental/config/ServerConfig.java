package com.epam.carrental.config;

import com.epam.carrental.services.*;
import com.sun.net.httpserver.HttpHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan (basePackages = {"com.epam.carrental"})
public class ServerConfig {

    @Value("${remote.service.info}")
    private String serviceInfoPath;

    @Value("${remote.service.car}")
    private String carServicePath;

    @Value("${remote.service.customer}")
    private String customerServicePath;

    @Value("${remote.port}")
    private String port;

    @Bean
    public ServerInfo serverInfo() {
        return new ServerInfoImpl();
    }

    @Bean
    public CarService carService(){return  new CarServiceImpl();}

    @Bean
    public CustomerService customerService(){return  new CustomerServiceImpl();}

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public SimpleHttpInvokerServiceExporter serverInfoServiceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.serverInfo());
        exporter.setServiceInterface(ServerInfo.class);
        return exporter;
    }
    @Bean
    public SimpleHttpInvokerServiceExporter carServiceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.carService());
        exporter.setServiceInterface(CarService.class);
        return exporter;
    }
    @Bean
    public SimpleHttpInvokerServiceExporter customerServiceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.customerService());
        exporter.setServiceInterface(CustomerService.class);
        return exporter;
    }
    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/"+ serviceInfoPath, serverInfoServiceExporter());
        contexts.put("/"+ carServicePath, carServiceExporter());
        contexts.put("/"+ customerServicePath, customerServiceExporter());
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        return serverFactory;
    }
}