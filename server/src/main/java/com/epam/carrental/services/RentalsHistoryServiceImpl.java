package com.epam.carrental.services;


import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.repository.RentalsHistoryRepository;
import com.epam.carrental.utils.RentReturnDateFilter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalsHistoryServiceImpl implements RentalsHistoryService {

    @Autowired
    RentalsHistoryRepository rentalsHistoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RentedCarHistoryDTO> findByDateOfRentAndDateOfReturn(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {

        RentReturnDateFilter rentReturnDateFilter = new RentReturnDateFilter(dateOfRent, dateOfReturn);

        return rentalsHistoryRepository.findAll().stream()
                .filter(rentReturnDateFilter)
                .map(rentedCarHistory -> modelMapper.map(rentedCarHistory, RentedCarHistoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RentedCarHistoryDTO> findAll() {
        Type listType = new TypeToken<List<RentedCarHistoryDTO>>() {
        }.getType();
        return modelMapper.map(rentalsHistoryRepository.findAll(), listType);
    }
}
