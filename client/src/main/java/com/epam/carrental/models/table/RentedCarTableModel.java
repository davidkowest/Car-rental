package com.epam.carrental.models.table;

import com.epam.carrental.dto.RentedCarDTO;
import org.springframework.stereotype.Component;


@Component
public class RentedCarTableModel extends AbstractSwingTableModel<RentedCarDTO> {
    public RentedCarTableModel(){
        this.tableName="Current rentals";
        this.columnAndActionMap.put("Registration number",rentedCarDTO -> rentedCarDTO.getCar().getRegistrationNumber());
        this.columnAndActionMap.put("Car model",rentedCarDTO -> rentedCarDTO.getCar().getModel());
        this.columnAndActionMap.put("Name",rentedCarDTO -> rentedCarDTO.getCustomer().getName());
        this.columnAndActionMap.put("Email",rentedCarDTO -> rentedCarDTO.getCustomer().getEmail());
        this.columnAndActionMap.put("Date of rent", RentedCarDTO::getStartDate);
        this.columnAndActionMap.put("Planned return date", RentedCarDTO::getEndDate);
    }
}

