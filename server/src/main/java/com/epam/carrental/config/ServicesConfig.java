package com.epam.carrental.config;

import com.epam.carrental.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;

@Configuration
@PropertySource({"classpath:services.properties", "classpath:dataGenerator.properties"})
@ComponentScan(basePackages = {"com.epam.carrental"})
@EnableAspectJAutoProxy
public class ServicesConfig {

    @Autowired
    ServerInfo serverInfo;

    @Autowired
    CarService carService;

    @Autowired
    CustomerService customerService;

    @Autowired
    RentReturnService rentReturnService;

    @Autowired
    CurrentRentalsService currentRentalsService;

    @Autowired
    RentalsHistoryService rentalsHistoryService;

    @Autowired
    RentalClassService rentalClassService;

    @Autowired
    BookCarService bookCarService;

    @Autowired
    ChartService chartService;

    @Autowired
    HistoryStatisticsService historyStatisticsService;

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
    public SimpleHttpInvokerServiceExporter rentReturnServiceExporter() {
        return serviceExporter(rentReturnService,RentReturnService.class);
    }
    @Bean
    public SimpleHttpInvokerServiceExporter currentRentalsServiceExporter() {
        return serviceExporter(currentRentalsService,CurrentRentalsService.class);
    }
    @Bean
    public SimpleHttpInvokerServiceExporter rentalsHistoryServiceExporter() {
        return serviceExporter(rentalsHistoryService,RentalsHistoryService.class);
    }

    @Bean
    public SimpleHttpInvokerServiceExporter rentalClassServiceExporter() {
        return serviceExporter(rentalClassService, RentalClassService.class);
    }

    @Bean
    public SimpleHttpInvokerServiceExporter bookCarServiceExporter() {
        return serviceExporter(bookCarService, BookCarService.class);
    }

    @Bean
    public SimpleHttpInvokerServiceExporter chartServiceExporter() {
        return serviceExporter(chartService, ChartService.class);
    }

    @Bean
    public SimpleHttpInvokerServiceExporter historyStatisticsExporter() {
        return serviceExporter(historyStatisticsService, HistoryStatisticsService.class);
    }

    public SimpleHttpInvokerServiceExporter serviceExporter(Object service, Class<?> serviceInterface) {
        SimpleHttpInvokerServiceExporter exporter = new CustomSimpleHttpInvokerServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(serviceInterface);
        return exporter;
    }
}