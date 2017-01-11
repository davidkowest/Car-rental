package com.epam.carrental.bookings;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class AvailableToBookCarTableModel extends AbstractSwingTableModel<CarDTO> {

    public AvailableToBookCarTableModel(){
        this.tableName="Available to book";
        this.columnAndActionMap.put("Registration number", CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }
}
