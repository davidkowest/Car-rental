package com.epam.carrental.config;

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
public class ClientConfig {

    @Value("${remote.protocol}://${remote.ip}:${remote.port}")
    private String url;

    @Value("${remote.service.info}")
    private String serviceInfoMapping;

    @Bean
    public HttpInvokerProxyFactoryBean serverInfoService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl(url+"/"+serviceInfoMapping);
        proxy.setServiceInterface(ServerInfo.class);
        return proxy;
    }

}