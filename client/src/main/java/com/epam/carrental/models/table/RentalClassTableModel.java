package com.epam.carrental.models.table;

import com.epam.carrental.dto.RentalClassDTO;
import org.springframework.stereotype.Component;

@Component
public class RentalClassTableModel extends AbstractSwingTableModel<RentalClassDTO> {

    public RentalClassTableModel(){
        this.tableName="Rental classes";
        this.columnAndActionMap.put("Name",RentalClassDTO::getName);
        this.columnAndActionMap.put("Hourly rental rate ($)",RentalClassDTO::getHourlyRate);
    }
}