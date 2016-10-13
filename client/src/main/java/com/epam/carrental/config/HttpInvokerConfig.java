package com.epam.carrental.config;

import com.epam.carrental.services.CarService;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages = {"com.epam.carrental"})
public class HttpInvokerConfig {

    @Value("${remote.protocol}://${remote.ip}:${remote.port}")
    private String url;

    @Value("${remote.service.info}")
    private String serviceInfoPath;

    @Value("${remote.service.car}")
    private String carServicePath;

    @Value("${remote.service.customer}")
    private String customerServicePath;

    @Bean
    public HttpInvokerProxyFactoryBean serverInfoService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url + "/" + serviceInfoPath);
        proxy.setServiceInterface(ServerInfo.class);
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean carService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url + "/" + carServicePath);
        proxy.setServiceInterface(CarService.class);
        return proxy;
    }

    @Bean
    public HttpInvokerProxyFactoryBean customerService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url + "/" + customerServicePath);
        proxy.setServiceInterface(CustomerService.class);
        return proxy;
    }
}