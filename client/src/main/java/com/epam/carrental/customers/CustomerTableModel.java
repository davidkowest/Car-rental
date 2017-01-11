package com.epam.carrental.customers;

import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerTableModel extends AbstractSwingTableModel<CustomerDTO> {

    public CustomerTableModel(){
        this.tableName="Customers";
        this.columnAndActionMap.put("Name", CustomerDTO::getName);
        this.columnAndActionMap.put("Email",CustomerDTO::getEmail);
    }
}
