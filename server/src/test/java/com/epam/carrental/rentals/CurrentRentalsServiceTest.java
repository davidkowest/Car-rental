package com.epam.carrental.rentals;

import com.epam.carrental.bookings.BookedCar;
import com.epam.carrental.cars.Car;
import com.epam.carrental.customers.Customer;
import com.epam.carrental.data_generator.CurrentTimeUtil;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.bookings.BookedCarRepository;
import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.rentals.CurrentRentalsServiceImpl;
import com.epam.carrental.rentals.RentedCar;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import com.epam.carrental.rentals.RentedCarRepository;
import com.epam.carrental.services.CurrentRentalsService;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.*;

public class CurrentRentalsServiceTest {


    private CurrentRentalsService currentRentalsService;
    private CarRepository carRepositoryMock;
    private RentalClassRepository rentalClassRepositoryMock;
    private RentedCarRepository rentedCarRepositoryMock;
    private BookedCarRepository bookedCarRepositoryMock;
    private ModelMapper modelMapper = new ModelMapper();
    private CurrentTimeUtil currentTimeUtilMock;

    @BeforeMethod
    public void setUp() {
        CurrentRentalsServiceImpl currentRentalsServiceImpl = new CurrentRentalsServiceImpl();
        this.carRepositoryMock = createStrictMock(CarRepository.class);
        this.rentedCarRepositoryMock = createStrictMock(RentedCarRepository.class);
        this.rentalClassRepositoryMock = createStrictMock(RentalClassRepository.class);
        this.bookedCarRepositoryMock = createStrictMock(BookedCarRepository.class);
        this.currentTimeUtilMock = createStrictMock(CurrentTimeUtil.class);
        currentRentalsServiceImpl.modelMapper = modelMapper;
        currentRentalsServiceImpl.carRepository = carRepositoryMock;
        currentRentalsServiceImpl.rentedCarRepository = rentedCarRepositoryMock;
        currentRentalsServiceImpl.rentalClassRepository = rentalClassRepositoryMock;
        currentRentalsServiceImpl.bookedCarRepository = bookedCarRepositoryMock;
        currentRentalsServiceImpl.currentTimeUtil = currentTimeUtilMock;
        this.currentRentalsService = currentRentalsServiceImpl;
    }

    @Test
    public void findNotRentedAndNotBookedTest() {
        //arrange
        RentalClassDTO rentalClassDTO = new RentalClassDTO("Economy", 2.45f);
        RentalClass rentalClass = modelMapper.map(rentalClassDTO, RentalClass.class);

        Car rentedCar = new Car("VW GOL IV", "KR12345", rentalClass);
        Car notRentedCar = new Car("VW GOL V", "KR12346", rentalClass);
        Car bookedCar = new Car("VW GOL V", "KR9876", rentalClass);
        CarDTO notRentedCarDTO = modelMapper.map(notRentedCar, CarDTO.class);
        CarDTO unBookedCarDTO = modelMapper.map(bookedCar, CarDTO.class);

        Customer customer = new Customer("Adam Malysz", "adam.malysz@gmail.com");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Moscow"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));
        ZonedDateTime startDateOfBooking = ZonedDateTime.of(LocalDateTime.of(2016, 10, 25, 10, 0), ZoneId.of("Europe/Moscow"));
        ZonedDateTime endDateOfBooking = ZonedDateTime.of(LocalDateTime.of(2016, 10, 28, 10, 0), ZoneId.of("Europe/Moscow"));

        List<RentedCar> rentedCarList = Collections.singletonList(new RentedCar(rentedCar, customer, rentingDate, plannedReturnDate));
        List<BookedCar> bookedCarList = Collections.singletonList(new BookedCar(bookedCar, customer, startDateOfBooking, endDateOfBooking));
        List<CarDTO> expectedAvailableToRent = Arrays.asList(notRentedCarDTO, unBookedCarDTO);


        EasyMock.expect(rentedCarRepositoryMock.findAll()).andReturn(rentedCarList);
        EasyMock.expect(carRepositoryMock.findAll()).andReturn(Arrays.asList(rentedCar, notRentedCar, bookedCar));
        EasyMock.expect(rentalClassRepositoryMock.findByName("Economy")).andReturn(rentalClass);
        EasyMock.expect(bookedCarRepositoryMock.findAll()).andReturn(bookedCarList);
        EasyMock.expect(currentTimeUtilMock.getCurrentTime()).andReturn(rentingDate);
        replay(currentTimeUtilMock, bookedCarRepositoryMock, rentalClassRepositoryMock, carRepositoryMock, rentedCarRepositoryMock);

        //act
        List<CarDTO> availableToRent = currentRentalsService.findAvailableToRent(rentalClassDTO, plannedReturnDate);

        //assert
        verify(carRepositoryMock);
        verify(rentedCarRepositoryMock);
        verify(rentalClassRepositoryMock);
        Assert.assertEquals(availableToRent, expectedAvailableToRent);
    }

    @Test
    public void testFindCurrentRentals() {
        //arrange
        RentalClass rentalClass = new RentalClass("Economy", 2.45f);
        Car rentedCar = new Car("VW GOL IV", "KR12345", rentalClass);
        Customer customer = new Customer("Adam Malysz", "adam.malysz@gmail.com");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));

        RentedCar currentRentedCar = new RentedCar(rentedCar, customer, rentingDate, plannedReturnDate);
        RentedCarDTO currentRentedCarDTO = modelMapper.map(currentRentedCar, RentedCarDTO.class);

        EasyMock.expect(rentedCarRepositoryMock.findAll()).andReturn(Collections.singletonList(currentRentedCar));
        replay(rentedCarRepositoryMock);


        List<RentedCarDTO> expectedRentedCarsList = Collections.singletonList(currentRentedCarDTO);

        //act
        List<RentedCarDTO> resultRentedCarList = currentRentalsService.findAll();

        //assert
        verify(rentedCarRepositoryMock);
        Assert.assertEquals(resultRentedCarList, expectedRentedCarsList);
    }

}