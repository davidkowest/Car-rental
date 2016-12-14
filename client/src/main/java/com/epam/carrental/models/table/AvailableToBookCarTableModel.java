package com.epam.carrental.models.table;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class AvailableToBookCarTableModel extends AbstractSwingTableModel<CarDTO> {

    public AvailableToBookCarTableModel(){
        this.tableName="Available to book";
        this.columnAndActionMap.put("Registration number", CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }
}
