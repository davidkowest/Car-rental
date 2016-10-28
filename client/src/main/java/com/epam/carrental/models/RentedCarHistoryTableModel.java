package com.epam.carrental.models;

import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.dto.RentedCarHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class RentedCarHistoryTableModel extends AbstractSwingTableModel<RentedCarHistoryDTO> {

    public RentedCarHistoryTableModel(){
        this.tableName="Rental history";
        this.columnAndActionMap.put("Registration number",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCar().getRegistrationNumber());
        this.columnAndActionMap.put("Car model",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCar().getModel());
        this.columnAndActionMap.put("Name",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCustomer().getName());
        this.columnAndActionMap.put("Email",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCustomer().getEmail());
        this.columnAndActionMap.put("Date of rent ", RentedCarHistoryDTO::getDateOfRent);
        this.columnAndActionMap.put("Date of return", RentedCarHistoryDTO::getDateOfReturn);
        this.columnAndActionMap.put("Duration", RentedCarHistoryDTO::getDuration);
    }
}