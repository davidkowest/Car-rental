package com.epam.carrental.data_generator;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.services.CarService;
import com.epam.carrental.services.CurrentRentalsService;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.RentReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@Conditional(GenerateCondition.class)
public class DataGenerator {

    @Autowired
    private CarGenerator carGenerator;

    @Autowired
    private CustomerGenerator customerGenerator;

    @Autowired
    private CarService carService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RentReturnService rentReturnService;

    @Autowired
    private CurrentRentalsService currentRentalsService;

    @Autowired
    private ZonedDateTimeGenerator zonedDateTimeGenerator;

    @Autowired
    private CurrentTimeUtil currentTimeUtil;

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    @Value("${rented.time.initial}")
    private String timeInitial;

    private List<CarDTO> availableCars;
    private List<CustomerDTO> customers;

    @PostConstruct
    public void generateAndSaveData() {
        generateCarsAndCustomers();
        saveCarsAndCustomers();
        generateAndSaveRentalHistory();
    }

    private void generateCarsAndCustomers() {
        this.availableCars = carGenerator.generateCars();
        this.customers = customerGenerator.generateCustomers();
    }

    private void saveCarsAndCustomers() {
        this.availableCars.forEach(carService::create);
        this.customers.forEach(customerService::create);
    }

    private void generateAndSaveRentalHistory() {

        for (CarDTO car : availableCars) {
            ZonedDateTime beginTime = ZonedDateTime.parse(timeInitial);

            while (beginTime.isBefore(ZonedDateTime.now())) {
                ZonedDateTime rentedTime = zonedDateTimeGenerator.getTimeAfter(beginTime);
                ZonedDateTime returnedTime = zonedDateTimeGenerator.getTimeAfter(rentedTime);
                if (returnedTime.isBefore(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))) {
                    rentCar(car, rentedTime);
                    RentedCarDTO rentedCarFromDB = currentRentalsService.findCurrentRentals().get(0);
                    returnCar(rentedCarFromDB, returnedTime);
                }
                beginTime = returnedTime;
            }
        }
        currentTimeUtil.setCurrentTime(null);
    }

    private void returnCar(RentedCarDTO rentedCarFromDB, ZonedDateTime returnedTime) {
        currentTimeUtil.setCurrentTime(returnedTime);
        rentReturnService.returnRentedCar(rentedCarFromDB);
    }

    private void rentCar(CarDTO car, ZonedDateTime rentedTime) {
        RentedCarDTO rentedCarDTO = new RentedCarDTO(car, getRandomCustomer());
        currentTimeUtil.setCurrentTime(rentedTime);
        rentReturnService.rentCarForCustomer(rentedCarDTO);
    }

    private CustomerDTO getRandomCustomer() {
        return customers.get(randomNumberGenerator.generateWithin(0, customers.size()));
    }

}
