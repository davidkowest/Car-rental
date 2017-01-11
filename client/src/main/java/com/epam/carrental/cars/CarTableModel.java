package com.epam.carrental.cars;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CarTableModel extends AbstractSwingTableModel<CarDTO> {

    public CarTableModel(){
        this.tableName="Fleet";
        this.columnAndActionMap.put("Registration number",CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
        this.columnAndActionMap.put("Rental class",carDTO -> carDTO.getRentalClass().getName());
    }

}