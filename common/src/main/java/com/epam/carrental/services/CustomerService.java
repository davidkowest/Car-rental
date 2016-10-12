package com.epam.carrental.services;

import com.epam.carrental.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    Boolean create(CustomerDTO customerDTO);

    List<CustomerDTO> readAll();
}