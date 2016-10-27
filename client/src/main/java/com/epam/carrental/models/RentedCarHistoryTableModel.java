package com.epam.carrental.models;

import com.epam.carrental.dto.RentedCarDTO;
import org.springframework.stereotype.Component;

@Component
public class RentedCarHistoryTableModel extends AbstractSwingTableModel<RentedCarDTO> {

    public RentedCarHistoryTableModel(){
        this.tableName="Rental history";
        this.columnAndActionMap.put("Registration number",rentedCarDTO -> rentedCarDTO.getCar().getRegistrationNumber());
        this.columnAndActionMap.put("Car model",rentedCarDTO -> rentedCarDTO.getCar().getModel());
        this.columnAndActionMap.put("Name",rentedCarDTO -> rentedCarDTO.getCustomer().getName());
        this.columnAndActionMap.put("Email",rentedCarDTO -> rentedCarDTO.getCustomer().getEmail());
        this.columnAndActionMap.put("Date of rent ", RentedCarDTO::getDateOfRent);
        this.columnAndActionMap.put("Date of return", RentedCarDTO::getDateOfReturn);
        this.columnAndActionMap.put("Duration", RentedCarDTO::getDuration);
    }
}