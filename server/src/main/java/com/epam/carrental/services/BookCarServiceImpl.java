package com.epam.carrental.services;


import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.entity.*;
import com.epam.carrental.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookCarServiceImpl implements BookCarService {

    @Autowired
    BookedCarRepository bookCarRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RentalClassRepository rentalClassRepository;
    @Autowired
    RentedCarRepository rentedCarRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void bookCar(BookedCarDTO bookedCarDTO) {
        Car car = carRepository.findByRegistrationNumber(bookedCarDTO.getCar().getRegistrationNumber());
        Customer customer = customerRepository.findByEmail(bookedCarDTO.getCustomer().getEmail());

        List<CarDTO> avaiableCars = findAvailableToBook(bookedCarDTO.getStartDate(), bookedCarDTO.getEndDate(), bookedCarDTO.getCar().getRentalClass());
        if (!avaiableCars.contains(bookedCarDTO.getCar())) {
            throw new IllegalArgumentException("This car is not available for this period");
        }

        BookedCar bookedCar = modelMapper.map(bookedCarDTO, BookedCar.class);
        bookedCar.setCar(car);
        bookedCar.setCustomer(customer);
        bookCarRepository.save(bookedCar);
    }

    @Override
    public List<CarDTO> findAvailableToBook(ZonedDateTime startDate, ZonedDateTime endDate, RentalClassDTO rentalClassDTO) {

        if (rentalClassDTO == null) {
            throw new IllegalArgumentException("Rental class should be specified before");
        }
        RentalClass rentalClass = rentalClassRepository.findByName(rentalClassDTO.getName());

        List<Car> rentedCars = rentedCarRepository.findAll()
                .stream()
                .filter(rentedCar -> rentedCar.getPlannedDateOfReturn().isAfter(startDate))
                .map(RentedCar::getCar)
                .collect(Collectors.toList());

        List<Car> bookedCars = bookCarRepository
                .findAll()
                .stream()
                .filter(bookedCar -> bookedCar.getStartDate().isBefore(endDate) && bookedCar.getEndDate().isAfter(startDate))
                .map(BookedCar::getCar)
                .collect(Collectors.toList());

        return carRepository.findAll()
                .stream()
                .filter(car -> !bookedCars.contains(car))
                .filter(car -> !rentedCars.contains(car))
                .filter(car -> car.getRentalClass().equals(rentalClass))
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());

    }
}