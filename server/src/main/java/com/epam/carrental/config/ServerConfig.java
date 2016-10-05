package com.epam.carrental.config;

import com.epam.carrental.services.ServerInfo;
import com.epam.carrental.services.ServerInfoImpl;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ServerConfig {

    @Bean
    public ServerInfo serverInfo() {
        return new ServerInfoImpl();
    }

    @Bean
    public SimpleHttpInvokerServiceExporter serviceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.serverInfo());
        exporter.setServiceInterface(ServerInfo.class);
        return exporter;
    }

    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<String, HttpHandler>();
        contexts.put("/serverInfo", serviceExporter());
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(8080);
        return serverFactory;
    }
}