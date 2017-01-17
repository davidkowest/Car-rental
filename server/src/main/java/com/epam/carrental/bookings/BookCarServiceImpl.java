package com.epam.carrental.bookings;


import com.epam.carrental.cars.Car;
import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.customers.Customer;
import com.epam.carrental.customers.CustomerRepository;
import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.performance.PerformanceMonitor;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import com.epam.carrental.rentals.RentedCar;
import com.epam.carrental.rentals.RentedCarRepository;
import com.epam.carrental.services.BookCarService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
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

        List<CarDTO> availableCars = findAvailableToBook(bookedCarDTO.getStartDate(), bookedCarDTO.getEndDate(), bookedCarDTO.getCar().getRentalClass());
        if (!availableCars.contains(bookedCarDTO.getCar())) {
            throw new IllegalArgumentException("This car is not available for this period");
        }

        BookedCar bookedCar = modelMapper.map(bookedCarDTO, BookedCar.class);
        bookedCar.setCar(car);
        bookedCar.setCustomer(customer);
        bookCarRepository.save(bookedCar);
    }

    @Override
    @PerformanceMonitor
    public List<CarDTO> findAvailableToBook(ZonedDateTime startDate, ZonedDateTime endDate, RentalClassDTO rentalClassDTO) {
        if (rentalClassDTO == null) {
            throw new IllegalArgumentException("Rental class should be specified before");
        }
        RentalClass rentalClass = rentalClassRepository.findByName(rentalClassDTO.getName());

        List<Car> rentedCars = rentedCarRepository.findAll()
                .stream()
                .filter(rentedCar -> rentedCar.getEndDate().isAfter(startDate))
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

    @Override
    public List<BookedCarDTO> findAll() {
        Type listType = new TypeToken<List<BookedCarDTO>>() {}.getType();
        return modelMapper.map(bookCarRepository.findAll(),listType);
    }
}