package com.epam.carrental.models;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class AvailableToRentCarTableModel extends AbstractSwingTableModel<CarDTO> {

    public AvailableToRentCarTableModel(){
        this.tableName="Available to rent";
        this.columnAndActionMap.put("Registration number",CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }
}

