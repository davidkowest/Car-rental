package com.epam.carrental.bookings;

import com.epam.carrental.cars.Car;
import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.customers.Customer;
import com.epam.carrental.customers.CustomerRepository;
import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.rentals.RentedCarRepository;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import com.epam.carrental.rentals.RentedCar;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;


public class BookCarServiceImplTest {

    @InjectMocks
    BookCarServiceImpl bookCarServiceImpl = new BookCarServiceImpl();

    @Mock
    private CarRepository carRepositoryMock;
    @Mock
    private BookedCarRepository bookedCarRepositoryMock;
    @Mock
    private RentalClassRepository rentalClassRepositoryMock;
    @Mock
    private CustomerRepository customerRepositoryMock;
    @Mock
    private RentedCarRepository rentedCarRepositoryMock;

    private ModelMapper modelMapper = new ModelMapper();

    private List<Car> cars = new ArrayList<>();
    private List<BookedCar> bookedCars = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        generateCars();
        generateBookedCars();
        when(carRepositoryMock.findAll()).thenReturn(cars);
        when(carRepositoryMock.findByRegistrationNumber(any())).thenReturn(new Car());
        when(bookedCarRepositoryMock.findAll()).thenReturn(bookedCars);
        when(rentalClassRepositoryMock.findByName(any())).thenReturn(new RentalClass());
        when(customerRepositoryMock.findByEmail(any())).thenReturn(new Customer());

        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        bookCarServiceImpl.modelMapper = this.modelMapper;
    }


    @Test(dataProvider = "getCarDTO")
    public void testFindAvailableCarsWithoutRented(CarDTO mayBeAvailableCar, boolean expected, String message) {
        // arrange
        when(rentedCarRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // act
        List<CarDTO> availableCars = bookCarServiceImpl.findAvailableToBook(getTime("2017-01-01T10:10:00Z"), getTime("2017-01-08T10:10:00Z"), new RentalClassDTO());

        //assert
        assertEquals(availableCars.contains(mayBeAvailableCar), expected, message);
    }

    @Test
    public void testFindAvailableCarsWithRented() {
        // arrange
        RentedCar rentedCar1 = new RentedCar(cars.get(5),getCustomer(),getTime("2016-12-01T10:10:00Z"),getTime("2016-12-07T10:10:00Z")); // must be available
        RentedCar rentedCar2 = new RentedCar(cars.get(0),getCustomer(),getTime("2017-01-01T10:10:00Z"),getTime("2017-01-07T10:10:00Z")); // must be NOT available
        List<RentedCar> rentedCars = Arrays.asList(rentedCar1,rentedCar2);

        when(rentedCarRepositoryMock.findAll()).thenReturn(rentedCars);

        CarDTO expectedCar1 = mapCarToCarDTO(rentedCar1.getCar());
        CarDTO expectedCar2 = mapCarToCarDTO(rentedCar2.getCar());

        // act
        List<CarDTO> availableCars = bookCarServiceImpl.findAvailableToBook(getTime("2017-01-01T10:10:00Z"), getTime("2017-01-08T10:10:00Z"), new RentalClassDTO());

        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(availableCars.contains(expectedCar1),"Expected "+expectedCar1.getModel()+" to be available");
        sa.assertFalse(availableCars.contains(expectedCar2),"Expected "+expectedCar2.getModel()+" to be NOT available");
        sa.assertAll();
    }

    @Test
    public void testBookAvailableCar() {
        // arrange in BeforeMethod

        // act
        ZonedDateTime bookFrom = getTime("2017-01-01T10:10:00Z");
        ZonedDateTime bookTo = getTime("2017-01-08T10:10:00Z");
        CustomerDTO customerDTO = new CustomerDTO(getCustomer().getName(), getCustomer().getEmail());

        CarDTO carDTO1 = mapCarToCarDTO(cars.get(0));
        CarDTO carDTO2 = mapCarToCarDTO(cars.get(3));
        CarDTO carDTO3 = mapCarToCarDTO(cars.get(4));
        BookedCarDTO bookedCarDTO1 = new BookedCarDTO(carDTO1, customerDTO, bookFrom, bookTo);
        BookedCarDTO bookedCarDTO2 = new BookedCarDTO(carDTO2, customerDTO, bookFrom, bookTo);
        BookedCarDTO bookedCarDTO3 = new BookedCarDTO(carDTO3, customerDTO, bookFrom, bookTo);
        BookedCarDTO bookedCarDTO4 = new BookedCarDTO(carDTO3, customerDTO, bookFrom, bookTo);

        bookCarServiceImpl.bookCar(bookedCarDTO1);
        bookCarServiceImpl.bookCar(bookedCarDTO2);
        bookCarServiceImpl.bookCar(bookedCarDTO3);
        bookCarServiceImpl.bookCar(bookedCarDTO4);

        //assert
        verify(bookedCarRepositoryMock, times(4)).save(any(BookedCar.class));

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBookNotAvailableBecauseBooked() {
        // arrange in BeforeMethod

        // act
        ZonedDateTime bookFrom = getTime("2017-01-01T10:10:00Z");
        ZonedDateTime bookTo = getTime("2017-01-08T10:10:00Z");
        CustomerDTO customerDTO = new CustomerDTO(getCustomer().getName(), getCustomer().getEmail());
        CarDTO carDTO1 = mapCarToCarDTO(cars.get(2));
        BookedCarDTO bookedCarDTO1 = new BookedCarDTO(carDTO1, customerDTO, bookFrom, bookTo);

        bookCarServiceImpl.bookCar(bookedCarDTO1);

        //assert Exception
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testBookNotAvailableBecauseRented() {
        // arrange
        RentedCar rentedCar = new RentedCar(cars.get(0),getCustomer(),getTime("2017-01-01T10:10:00Z"),getTime("2017-01-07T10:10:00Z"));
        List<RentedCar> rentedCars = Collections.singletonList(rentedCar);
        when(rentedCarRepositoryMock.findAll()).thenReturn(rentedCars);

        // act
        ZonedDateTime bookFrom = getTime("2017-01-03T10:10:00Z");
        ZonedDateTime bookTo = getTime("2017-01-08T10:10:00Z");
        CustomerDTO customerDTO = new CustomerDTO(getCustomer().getName(), getCustomer().getEmail());
        CarDTO carDTO = mapCarToCarDTO(cars.get(0));
        BookedCarDTO bookedCarDTO = new BookedCarDTO(carDTO, customerDTO, bookFrom, bookTo);

        bookCarServiceImpl.bookCar(bookedCarDTO);

        //assert Exception
    }

    @DataProvider(name = "getCarDTO")
    public Object[][] getCarDTO() {
        return new Object[][]{
                {mapCarToCarDTO(bookedCars.get(0).getCar()), true, getMessage() + bookedCars.get(0).getStartDate() + " to " + bookedCars.get(0).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(1).getCar()), true, getMessage() + bookedCars.get(1).getStartDate() + " to " + bookedCars.get(1).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(2).getCar()), false, getMessage() + bookedCars.get(2).getStartDate() + " to " + bookedCars.get(2).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(3).getCar()), false, getMessage() + bookedCars.get(3).getStartDate() + " to " + bookedCars.get(3).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(4).getCar()), false, getMessage() + bookedCars.get(4).getStartDate() + " to " + bookedCars.get(4).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(5).getCar()), false, getMessage() + bookedCars.get(5).getStartDate() + " to " + bookedCars.get(5).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(6).getCar()), true, getMessage() + bookedCars.get(6).getStartDate() + " to " + bookedCars.get(6).getEndDate()},
                {mapCarToCarDTO(bookedCars.get(7).getCar()), true, getMessage() + bookedCars.get(7).getStartDate() + " to " + bookedCars.get(7).getEndDate()},
                {mapCarToCarDTO(new Car(cars.get(5).getModel(), cars.get(5).getRegistrationNumber(), new RentalClass())), true, "This car was never booked"} // NOT booked car
        };
    }

    private void generateCars() {
        cars.add(new Car("Nissan 300ZX", "KHZ123", new RentalClass()));  // 0: booked twice: before and after, now available
        cars.add(new Car("Jeep Cherokee", "KHZ234", new RentalClass())); // 1: booked three times: before-in, within, in-after
        cars.add(new Car("Kia Sportage", "KHZ567", new RentalClass()));  // 2: booked once before-after
        cars.add(new Car("Honda Accord", "KHZ678", new RentalClass()));  // 3: booked once: before-just on the age
        cars.add(new Car("Subaru Legacy", "KHZ789", new RentalClass())); // 4: booked once: just on the age-after
        cars.add(new Car("Lexus LS", "KHZ", new RentalClass()));         // 5: NOT booked
    }

    private void generateBookedCars() {
        bookedCars.add(new BookedCar(cars.get(0), getCustomer(), getTime("2016-12-20T10:10:00Z"), getTime("2016-12-25T10:10:00Z"))); // 0: booked and unbooked before testing period
        bookedCars.add(new BookedCar(cars.get(0), getCustomer(), getTime("2017-01-15T10:10:00Z"), getTime("2017-01-20T10:10:00Z"))); // 1: booked and unbooked after testing period
        bookedCars.add(new BookedCar(cars.get(1), getCustomer(), getTime("2016-12-20T10:10:00Z"), getTime("2017-01-02T10:10:00Z"))); // 2: booked before testing period, unbooked within it
        bookedCars.add(new BookedCar(cars.get(1), getCustomer(), getTime("2017-01-03T10:10:00Z"), getTime("2017-01-05T10:10:00Z"))); // 3: booked and unbooked within testing period
        bookedCars.add(new BookedCar(cars.get(1), getCustomer(), getTime("2017-01-06T10:10:00Z"), getTime("2017-01-10T10:10:00Z"))); // 4: booked within testing period,unbooked after it
        bookedCars.add(new BookedCar(cars.get(2), getCustomer(), getTime("2016-12-20T10:10:00Z"), getTime("2017-01-10T10:10:00Z"))); // 5: booked before testing period,unbooked after it
        bookedCars.add(new BookedCar(cars.get(3), getCustomer(), getTime("2017-01-08T10:10:00Z"), getTime("2017-01-21T10:10:00Z"))); // 6: booked just on the age, unbooked after testing period
        bookedCars.add(new BookedCar(cars.get(4), getCustomer(), getTime("2016-12-20T10:10:00Z"), getTime("2017-01-01T10:10:00Z"))); // 7: booked before testing period,unbooked just on the age
    }

    private CarDTO mapCarToCarDTO(Car car) {
        return modelMapper.map(car,CarDTO.class);
       // return new CarDTO(car.getModel(), car.getRegistrationNumber(), new RentalClassDTO(car.getRentalClass().getName(), car.getRentalClass().getHourlyRate()));
    }

    private Customer getCustomer() {
        return new Customer("Robin Bobbin", "Bobbin@gmail.com");
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

    private String getMessage() {
        return "Car is not available because it is booked for period from ";
    }
}