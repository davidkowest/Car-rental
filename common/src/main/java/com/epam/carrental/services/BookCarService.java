package com.epam.carrental.services;


import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface BookCarService {

    void bookCar(BookedCarDTO bookedCarDTO);

    List<CarDTO> findAvailableCarsBy(ZonedDateTime startDate, ZonedDateTime endDate, RentalClassDTO rentalClassDTO);
}
