package com.epam.carrental.config;

import com.epam.carrental.gui.MainWindow;
import com.epam.carrental.services.ServerInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;

@Configuration
public class ClientConfig {
    @Bean
    public HttpInvokerProxyFactoryBean serverInfoService() {
        HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
        proxy.setServiceUrl("http://localhost:8080/serverInfo");
        proxy.setServiceInterface(ServerInfo.class);
        return proxy;
    }

    @Bean
    public MainWindow mainWindow(){
        return new MainWindow();
    }

}