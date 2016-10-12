package com.epam.carrental.models;

import com.epam.carrental.dto.CarDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarTableModel extends AbstractCarRentalTableModel<CarDTO> {

    public CarTableModel() {
        tableName = "Fleet";
        columnNames.add("RegistrationNumber");
        columnNames.add("Model");
        data = new ArrayList<>();
    }

    @Override
    public void setData (List<CarDTO> data) {
        this.data=data
                .stream()
                .map(d -> Arrays.asList(d.getRegistrationNumber(),d.getModel()))
                .collect(Collectors.toList());
    }
}
