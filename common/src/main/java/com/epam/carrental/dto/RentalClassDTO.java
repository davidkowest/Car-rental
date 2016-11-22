package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentalClassDTO implements Serializable {

    private String name;
    private float hourlyRate;

}