package com.epam.carrental.services;


import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.utils.RentReturnDateFilter;
import com.epam.carrental.repository.RentedCarHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalsHistoryServiceImpl implements RentalsHistoryService {

    @Autowired
    RentedCarHistoryRepository rentedCarHistoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RentReturnDateFilter rentReturnDateFilter;

    @Override
    public List<RentedCarHistoryDTO> findByDateOfRentAndDateOfReturn(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {

        return rentedCarHistoryRepository.findAll().stream()
                .filter(rentReturnDateFilter.filterBy(dateOfRent,dateOfReturn))
                .map(rentedCarHistory -> modelMapper.map(rentedCarHistory, RentedCarHistoryDTO.class))
                .collect(Collectors.toList());
    }
}
