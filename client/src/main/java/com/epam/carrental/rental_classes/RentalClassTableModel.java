package com.epam.carrental.rental_classes;

import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class RentalClassTableModel extends AbstractSwingTableModel<RentalClassDTO> {

    public RentalClassTableModel(){
        this.tableName="Rental classes";
        this.columnAndActionMap.put("Name",RentalClassDTO::getName);
        this.columnAndActionMap.put("Hourly rental rate ($)",RentalClassDTO::getHourlyRate);
    }
}