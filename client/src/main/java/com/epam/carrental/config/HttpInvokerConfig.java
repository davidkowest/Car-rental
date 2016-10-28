package com.epam.carrental.config;

import com.epam.carrental.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
@PropertySource({"classpath:application.properties","labels.properties"})
@ComponentScan(basePackages = {"com.epam.carrental"})
public class HttpInvokerConfig {

    @Value("${remote.protocol}://${remote.ip}:${remote.port}")
    private String url;

    private HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(String path,Class<?> serviceInterface){
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url + "/" + path);
        proxy.setServiceInterface(serviceInterface);
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
}