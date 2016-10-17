package com.epam.carrental.dto;

import java.io.Serializable;

public class RentedCarDTO implements Serializable {

    private final CarDTO carDTO;
    private final CustomerDTO customerDTO;

    public RentedCarDTO(CarDTO carDTO, CustomerDTO customerDTO) {
        this.carDTO = carDTO;
        this.customerDTO = customerDTO;
    }

    public CarDTO getCarDTO() {
        return carDTO;
    }
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

}
