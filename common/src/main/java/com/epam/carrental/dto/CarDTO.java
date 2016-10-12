package com.epam.carrental.dto;


import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CarDTO implements Serializable {

    @NotNull
    @NotEmpty
    private String model;
    @NotEmpty
    private String registrationNumber;

    public CarDTO() {
    }

    public CarDTO(String carModel, String carNumber) {
        this.model = carModel;
        this.registrationNumber = carNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }


    @Override
    public String toString() {
        return "Car " +
                "model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'';
    }
}
