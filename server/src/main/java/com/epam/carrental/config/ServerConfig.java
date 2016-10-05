package com.epam.carrental.config;

import com.epam.carrental.services.ServerInfo;
import com.epam.carrental.services.ServerInfoImpl;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.properties"})
public class ServerConfig {

    @Value("${remote.service.info}")
    private String serviceInfoMapping;

    @Value("${remote.port}")
    private String port;

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
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/"+serviceInfoMapping, serviceExporter());
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        return serverFactory;
    }
}