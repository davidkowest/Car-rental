package com.epam.carrental.config;

import com.epam.carrental.services.CarService;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.RentedCarService;
import com.epam.carrental.services.ServerInfo;
import com.sun.net.httpserver.HttpHandler;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

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

    @Value("${remote.service.rentedCar}")
    private String rentedCarServicePath;

    @Autowired
    ServerInfo serverInfo;

    @Autowired
    CarService carService;

    @Autowired
    CustomerService customerService;

    @Autowired
    RentedCarService rentedCarService;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return modelMapper;
    }
    @Bean
    public SimpleHttpInvokerServiceExporter serverInfoServiceExporter() {
        return  serviceExporter(serverInfo,ServerInfo.class);
    }
    @Bean
    public SimpleHttpInvokerServiceExporter carServiceExporter() {
        return serviceExporter(carService,CarService.class);
    }
    @Bean
    public SimpleHttpInvokerServiceExporter customerServiceExporter() {
        return serviceExporter(customerService,CustomerService.class);
    }
    @Bean
    public SimpleHttpInvokerServiceExporter rentedCarServiceExporter() {
        return serviceExporter(rentedCarService,RentedCarService.class);
    }

    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/"+ serviceInfoPath, serverInfoServiceExporter());
        contexts.put("/"+ carServicePath, carServiceExporter());
        contexts.put("/"+ customerServicePath, customerServiceExporter());
        contexts.put("/"+ rentedCarServicePath, rentedCarServiceExporter());
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        serverFactory.setExecutor(Executors.newFixedThreadPool(4));
        return serverFactory;
    }

    public SimpleHttpInvokerServiceExporter serviceExporter(Object service,Class<?> serviceInterface) {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(serviceInterface);
        return exporter;
    }
}