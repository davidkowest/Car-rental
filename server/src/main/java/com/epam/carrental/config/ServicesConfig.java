package com.epam.carrental.config;

import com.epam.carrental.services.CarService;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.RentedCarService;
import com.epam.carrental.services.ServerInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;

@Configuration
@PropertySource({"classpath:application.properties","classpath:dataGenerator.properties"})
@ComponentScan (basePackages = {"com.epam.carrental"})
public class ServicesConfig {

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

    public SimpleHttpInvokerServiceExporter serviceExporter(Object service,Class<?> serviceInterface) {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(serviceInterface);
        return exporter;
    }
}