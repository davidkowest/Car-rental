package com.epam.carrental.services;

import com.epam.carrental.dto.RentalClassDTO;

import java.util.List;

public interface RentalClassService {

    List<RentalClassDTO> readAll();

    void create(RentalClassDTO rentalClassDTO);
}