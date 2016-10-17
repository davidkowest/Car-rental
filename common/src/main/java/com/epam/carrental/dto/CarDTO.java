package com.epam.carrental.dto;


import java.io.Serializable;

public class CarDTO implements Serializable {

    private String model;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }


    @Override
    public String toString() {
        return "Car " +
                "model='" + model + '\'' +
                ", registrationNumber='" + registrationNumber + '\'';
    }
}
