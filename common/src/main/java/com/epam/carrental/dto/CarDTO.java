package com.epam.carrental.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
@NoArgsConstructor
public class CarDTO implements Serializable {

    @Getter
    private String model;
    @Getter
    private String registrationNumber;

    public CarDTO(String carModel, String carNumber) {
        this.model = carModel;
        this.registrationNumber = carNumber;
    }
}
