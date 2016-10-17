package com.epam.carrental.config;

import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.controller.FleetController;
import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.gui.view.builders.impl.TableTabView;
import com.epam.carrental.models.CarTableModel;
import com.epam.carrental.models.CustomerTableModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.epam.carrental"})
public class SwingConfig {

    @Bean
    CarTableModel carTableModel(){
        return new CarTableModel("Fleet", Arrays.asList("Registration number", "Car model"));
    }

    @Bean
    CarTableModel availableCarsTableModel(){
        return new CarTableModel("Available cars", Arrays.asList("Registration number", "Car model"));
    }

    @Bean
   CustomerTableModel customerTableModel(){
        return new CustomerTableModel("Customers", Arrays.asList("Name", "Email"));
    }

    @Bean
    public TableTabView fleetView(FleetController fleetController){
        Map<String,Runnable> actions=new LinkedHashMap<>();
        actions.put("Refresh table",fleetController::refreshTableView);
        actions.put("Add new car",fleetController::handleUserInput);
        return new TableTabView(carTableModel(), actions);
    }

    @Bean
    public TableTabView rentCarView(RentCarController rentCarController){
        Map<String,Runnable> actions=new LinkedHashMap<>();
        actions.put("Refresh table",rentCarController::refreshTableView);
        actions.put("Rent a car",rentCarController::handleUserInput);
        TableTabView rentCarView = new TableTabView(availableCarsTableModel(),actions);
        // to avoid unresolvable circular reference during creating RentCarController and RentCarView
        rentCarController.setRentCarView(rentCarView);
        return rentCarView;
    }

    @Bean
    public TableTabView customerView(CustomerController customerController){
        Map<String,Runnable> actions=new LinkedHashMap<>();
        actions.put("Refresh table",customerController::refreshTableView);
        actions.put("Add new customer",customerController::handleUserInput);
        return new TableTabView(customerTableModel(),actions);
    }
}
