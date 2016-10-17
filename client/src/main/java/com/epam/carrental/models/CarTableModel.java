package com.epam.carrental.models;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarTableModel extends AbstractSwingTableModel<CarDTO> {


    public CarTableModel(String tableName, List<String> columnNames){
       super(tableName, columnNames);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CarDTO carDTO = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return carDTO.getRegistrationNumber();
            case 1:
                return carDTO.getModel();

        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex){
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return String.class;
        }
        return null;
    }
}