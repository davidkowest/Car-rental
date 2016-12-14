package com.epam.carrental.config;

import com.epam.carrental.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerRequestExecutor;

@Configuration
@PropertySource({"classpath:application.properties","labels.properties"})
@ComponentScan(basePackages = {"com.epam.carrental"})
public class HttpInvokerConfig {


    @Autowired
    SimpleHttpInvokerRequestExecutor customSimpleHttpInvokerRequestExecutor;

    @Value("${remote.protocol}://${remote.ip}:${remote.port}")
    private String url;

    private HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(String path,Class<?> serviceInterface){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url + "/" + path);
        proxy.setServiceInterface(serviceInterface);
        proxy.setHttpInvokerRequestExecutor(customSimpleHttpInvokerRequestExecutor);
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean serverInfoService(@Value("${remote.service.info}") String serviceInfoPath) {
        return httpInvokerProxyFactoryBean(serviceInfoPath,ServerInfo.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean carService(@Value("${remote.service.car}") String carServicePath) {
        return httpInvokerProxyFactoryBean(carServicePath,CarService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean customerService(@Value("${remote.service.customer}") String customerServicePath) {
        return httpInvokerProxyFactoryBean(customerServicePath,CustomerService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean rentReturnCarService(@Value("${remote.service.rentReturnCar}") String rentReturnCarServicePath) {
        return httpInvokerProxyFactoryBean(rentReturnCarServicePath,RentReturnService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean currentRentalsService(@Value("${remote.service.currentRentals}") String currentRentalsServicePath) {
        return httpInvokerProxyFactoryBean(currentRentalsServicePath,CurrentRentalsService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean rentalHistoryService(@Value("${remote.service.rentalHistory}") String rentalHistoryServicePath) {
        return httpInvokerProxyFactoryBean(rentalHistoryServicePath,RentalsHistoryService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean rentalClassService(@Value("${remote.service.rentalClass}") String rentalClassServicePath) {
        return httpInvokerProxyFactoryBean(rentalClassServicePath,RentalClassService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean bookCarService(@Value("${remote.service.bookCar}") String bookCarServicePath) {
        return httpInvokerProxyFactoryBean(bookCarServicePath,BookCarService.class);
    }

    @Bean
    public HttpInvokerProxyFactoryBean chartService(@Value("${remote.service.chartService}") String bookCarServicePath) {
        return httpInvokerProxyFactoryBean(bookCarServicePath,ChartService.class);
    }
}