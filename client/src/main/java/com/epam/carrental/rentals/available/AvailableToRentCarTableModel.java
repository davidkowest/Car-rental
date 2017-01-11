package com.epam.carrental.rentals.available;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class AvailableToRentCarTableModel extends AbstractSwingTableModel<CarDTO> {

    public AvailableToRentCarTableModel(){
        this.tableName="Available to rent";
        this.columnAndActionMap.put("Registration number",CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }
}

