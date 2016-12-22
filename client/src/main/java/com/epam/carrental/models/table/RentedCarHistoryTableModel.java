package com.epam.carrental.models.table;

import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.gui.utils.CustomFormatter;
import org.springframework.stereotype.Component;

@Component
public class RentedCarHistoryTableModel extends AbstractSwingTableModel<RentedCarHistoryDTO> {

    public RentedCarHistoryTableModel(){
        this.tableName="Rental history";
        this.columnAndActionMap.put("Registration number",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCar().getRegistrationNumber());
        this.columnAndActionMap.put("Car model",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCar().getModel());
        this.columnAndActionMap.put("Name",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCustomer().getName());
        this.columnAndActionMap.put("Email",RentedCarHistoryDTO -> RentedCarHistoryDTO.getCustomer().getEmail());
        this.columnAndActionMap.put("Date of rent ", RentedCarHistoryDTO-> CustomFormatter.toShortFormat(RentedCarHistoryDTO.getStartDate()));
        this.columnAndActionMap.put("Date of return", RentedCarHistoryDTO->CustomFormatter.toShortFormat(RentedCarHistoryDTO.getEndDate()));
        this.columnAndActionMap.put("Duration", RentedCarHistoryDTO::getDuration);
    }
}