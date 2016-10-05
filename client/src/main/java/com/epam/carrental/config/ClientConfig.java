package com.epam.carrental.config;

import com.epam.carrental.services.ServerInfo;
import com.epam.carrental.settings.SharedSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class ClientConfig {
    @Bean
    public HttpInvokerProxyFactoryBean serverInfoService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl("http://localhost:"+ SharedSettings.PORT+"/"+SharedSettings.SERVER_INFO_SERVICE);
        proxy.setServiceInterface(ServerInfo.class);
        return proxy;
    }
}