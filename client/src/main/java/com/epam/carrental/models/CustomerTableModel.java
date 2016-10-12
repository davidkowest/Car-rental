package com.epam.carrental.models;

import com.epam.carrental.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerTableModel extends AbstractCarRentalTableModel<CustomerDTO> {

    public CustomerTableModel() {
        tableName = "Customers";
        columnNames.add("Customer Name");
        columnNames.add("Customer Email");
        data = new ArrayList<>();
    }

    @Override
    public void setData (List<CustomerDTO> data) {
        this.data=data
                .stream()
                .map(d -> Arrays.asList(d.getName(),d.getEmail()))
                .collect(Collectors.toList());
    }
}
