package com.epam.carrental.models.table;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

@Component
public class CarTableModel extends AbstractSwingTableModel<CarDTO> {

    public CarTableModel(){
        this.tableName="Fleet";
        this.columnAndActionMap.put("Registration number",CarDTO::getRegistrationNumber);
        this.columnAndActionMap.put("Car model",CarDTO::getModel);
    }

}