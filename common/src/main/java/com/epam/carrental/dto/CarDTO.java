package com.epam.carrental.dto;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDTO implements Serializable {

    private String model;
    private String registrationNumber;
    private RentalClassDTO rentalClass;
}
