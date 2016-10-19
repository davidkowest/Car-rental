package com.epam.carrental.models;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class AvailableCarTableModel extends AbstractSwingTableModel<CarDTO> {

    public AvailableCarTableModel(){
        this.tableName="Available cars";
        this.columnAndActionMap.put("Registration number",CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }
}

