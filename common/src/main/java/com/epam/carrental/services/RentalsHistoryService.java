package com.epam.carrental.services;


import com.epam.carrental.dto.RentedCarHistoryDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface RentalsHistoryService {
    List<RentedCarHistoryDTO> findByDateOfRentAndDateOfReturn(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn);

    List<RentedCarHistoryDTO> findAll();
}
