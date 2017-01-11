package com.epam.carrental.rental_classes;

import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.services.RentalClassService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class RentalClassServiceImpl implements RentalClassService {

    @Autowired
    RentalClassRepository rentalClassRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<RentalClassDTO> readAll() {
        Type listType = new TypeToken<List<RentalClassDTO>>() {}.getType();
        return modelMapper.map(rentalClassRepository.findAll(),listType);
    }

    @Override
    public void create(RentalClassDTO rentalClassDTO) {
        RentalClass rentalClass = modelMapper.map(rentalClassDTO, RentalClass.class);
        rentalClassRepository.save(rentalClass);
    }
}