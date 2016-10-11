package com.epam.carrental.config;

import com.epam.carrental.services.CarService;
import com.epam.carrental.services.CarServiceImpl;
import com.epam.carrental.services.ServerInfo;
import com.epam.carrental.services.ServerInfoImpl;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource({"classpath:application.properties"})
@ComponentScan (basePackages = {"com.epam.carrental"})
public class ServerConfig {

    @Value("${remote.service.info}")
    private String serviceInfoPath;

    @Value("${remote.service.car}")
    private String carServicePath;

    @Value("${remote.port}")
    private String port;

    @Bean
    public ServerInfo serverInfo() {
        return new ServerInfoImpl();
    }

    @Bean
    public CarService carRepositoryService(){return  new CarServiceImpl();}

    @Bean
    public SimpleHttpInvokerServiceExporter serverInfoServiceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.serverInfo());
        exporter.setServiceInterface(ServerInfo.class);
        return exporter;
    }
    @Bean
    public SimpleHttpInvokerServiceExporter carRepositoryServiceExporter() {
        SimpleHttpInvokerServiceExporter exporter = new SimpleHttpInvokerServiceExporter();
        exporter.setService(this.carRepositoryService());
        exporter.setServiceInterface(CarService.class);
        return exporter;
    }
    @Bean
    public SimpleHttpServerFactoryBean serverFactory() {
        Map<String, HttpHandler> contexts = new HashMap<>();
        contexts.put("/"+ serviceInfoPath, serverInfoServiceExporter());
        contexts.put("/"+ carServicePath, carRepositoryServiceExporter());
        SimpleHttpServerFactoryBean serverFactory = new SimpleHttpServerFactoryBean();
        serverFactory.setContexts(contexts);
        serverFactory.setPort(Integer.parseInt(port));
        return serverFactory;
    }
}