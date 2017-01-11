package com.epam.carrental.data_generator;

import com.epam.carrental.dto.*;
import com.epam.carrental.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Conditional(GenerateCondition.class)
public class DataGenerator {

    @Autowired
    ClassGenerator classGenerator;
    @Autowired
    RentalClassService rentalClassService;
    @Autowired
    private CarGenerator carGenerator;
    @Autowired
    private CustomerGenerator customerGenerator;
    @Autowired
    private ZonedDateTimeGenerator zonedDateTimeGenerator;
    @Autowired
    private RandomNumberGenerator randomNumberGenerator;
    @Autowired
    private CurrentTimeUtil currentTimeUtil;

    @Autowired
    private CarService carService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RentReturnService rentReturnService;
    @Autowired
    private CurrentRentalsService currentRentalsService;
    @Autowired
    private BookCarService bookCarService;

    @Value("${rented.time.initial}")
    private String timeInitial;

    @Value("${booked.time.end}")
    private String timeEnd;

    private List<RentalClassDTO> rentalClasses;
    private List<CarDTO> availableCars;
    private List<CustomerDTO> customers;

    @PostConstruct
    @SuppressWarnings("unused")
    public void generateAndSaveData() {
        generateInitialData();
        saveInitialData();
        generateAndSaveRentalHistory();
    }

    private void generateInitialData() {
        this.rentalClasses = classGenerator.generateRentalClasses();
        this.availableCars = carGenerator.generateCars(rentalClasses);
        this.customers = customerGenerator.generateCustomers();
    }

    private void saveInitialData() {
        this.rentalClasses.forEach(rentalClassService::create);
        this.availableCars.forEach(carService::create);
        this.customers.forEach(customerService::create);
    }

    private void generateAndSaveRentalHistory() {

        for (CarDTO car : availableCars) {
            ZonedDateTime startTime = ZonedDateTime.parse(timeInitial);
            ZonedDateTime endTime = ZonedDateTime.parse(timeEnd);

            while (startTime.isBefore(endTime)) {
                ZonedDateTime generatedStartTime = zonedDateTimeGenerator.getStartTimeAfter(startTime);
                ZonedDateTime generatedEndTime = zonedDateTimeGenerator.getEndTimeAfter(generatedStartTime);

                if (generatedStartTime.isBefore(ZonedDateTime.now())) { // "rentals only before today"
                    RentedCarDTO rentedCarDTO = new RentedCarDTO(car, getRandomCustomer(), generatedStartTime, generatedEndTime);
                    rentCar(rentedCarDTO);
                    RentedCarDTO rentedCarFromDB = findCurrentlyRentedCar(car);

                    if (generatedEndTime.isBefore(ZonedDateTime.now())) { // if true: this car will be returned, if false: this car will be in current rentals
                        returnCar(rentedCarFromDB, generatedEndTime);
                    }
                } else {
                    bookCar(car, generatedStartTime, generatedEndTime);
                }
                startTime = generatedEndTime;

            }
        }
        currentTimeUtil.setCurrentTime(null);
    }

    private void returnCar(RentedCarDTO rentedCarFromDB, ZonedDateTime returnedTime) {
        currentTimeUtil.setCurrentTime(returnedTime);
        rentReturnService.returnRentedCar(rentedCarFromDB);
    }

    private void rentCar(RentedCarDTO rentedCarDTO) {
        currentTimeUtil.setCurrentTime(rentedCarDTO.getStartDate());
        rentReturnService.rentCarForCustomer(rentedCarDTO);
    }

    private void bookCar(CarDTO car, ZonedDateTime bookedTime, ZonedDateTime unbookedTime) {
        BookedCarDTO bookedCarDTO = new BookedCarDTO(car, getRandomCustomer(), bookedTime, unbookedTime);
        bookCarService.bookCar(bookedCarDTO);
    }

    private RentedCarDTO findCurrentlyRentedCar(CarDTO car) {
        return currentRentalsService.findAll()
                .stream()
                .filter(rentedCar -> rentedCar.getCar().equals(car)).findAny().orElse(null);
    }

    private CustomerDTO getRandomCustomer() {
        return customers.get(randomNumberGenerator.generateWithin(0, customers.size()));
    }
}
