package com.epam.carrental.models;

import com.epam.carrental.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerTableModel extends AbstractSwingTableModel<CustomerDTO> {

    public CustomerTableModel(String tableName, List<String> columnNames) {
        super(tableName,columnNames);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CustomerDTO customerDTO = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return customerDTO.getName();
            case 1:
                return customerDTO.getEmail();

        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
        }
        return null;
    }
}