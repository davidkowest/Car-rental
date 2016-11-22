package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentalClassRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

public class CarServiceTest {

    private CarService carService;
    private CarRepository carRepositoryMock;
    private RentalClassRepository rentalClassRepositoryMock;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        CarServiceImpl carServiceimpl = new CarServiceImpl();
        this.carRepositoryMock=createStrictMock(CarRepository.class);
        this.rentalClassRepositoryMock=createStrictMock(RentalClassRepository.class);
        carServiceimpl.modelMapper=modelMapper;
        carServiceimpl.carRepository=carRepositoryMock;
        carServiceimpl.rentalClassRepository=rentalClassRepositoryMock;

        this.carService=carServiceimpl;
    }

    @Test
    public void carCreateTest() {
        //arrange
        RentalClassDTO rentalClassDTO=new RentalClassDTO("Economy",2.45f);
        RentalClass rentalClass=modelMapper.map(rentalClassDTO,RentalClass.class);

        CarDTO carDTO=new CarDTO("VW GOL IV","KR12345",rentalClassDTO);
        Car car=modelMapper.map(carDTO, Car.class);

        EasyMock.expect(rentalClassRepositoryMock.findByName("Economy")).andReturn(rentalClass);
        replay(rentalClassRepositoryMock);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(null);
        EasyMock.expect(carRepositoryMock.save(car)).andReturn(car);
        replay(carRepositoryMock);

        //act
        carService.create(carDTO);

        //assert
        verify(rentalClassRepositoryMock);
        verify(carRepositoryMock);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class})
    public void theSameCarCreationTwiceTest() {
        //arrange
        RentalClassDTO rentalClassDTO=new RentalClassDTO("Economy",2.45f);
        RentalClass rentalClass=modelMapper.map(rentalClassDTO,RentalClass.class);

        CarDTO carDTO=new CarDTO("VW GOL IV","KR12345",rentalClassDTO);
        Car car=modelMapper.map(carDTO, Car.class);

        EasyMock.expect(rentalClassRepositoryMock.findByName("Economy")).andReturn(rentalClass).times(2);
        replay(rentalClassRepositoryMock);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(null);
        EasyMock.expect(carRepositoryMock.save(car)).andReturn(car);
        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(car);
        EasyMock.expect(carRepositoryMock.save(car)).andThrow(new IllegalArgumentException());
        replay(carRepositoryMock);

        //act
        carService.create(carDTO);
        carService.create(carDTO);

        //assert
        verify(rentalClassRepositoryMock);
        verify(carRepositoryMock);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class})
    public void theCarWithoutRentalClassTest() {
        //arrange
        CarDTO carDTO=new CarDTO("VW GOL IV","KR12345",null);
        //act
        carService.create(carDTO);
    }

    @Test
    public void readAllCarsTest() {
        //arrange
        //arrange
        RentalClassDTO rentalClassDTO=new RentalClassDTO("Economy",2.45f);
        RentalClass rentalClass=modelMapper.map(rentalClassDTO,RentalClass.class);

        EasyMock.expect(carRepositoryMock.findAll()).andReturn(Arrays.asList(new Car("VW GOL IV","KR12345",rentalClass)));
        replay(carRepositoryMock);
        List<CarDTO> expectedCars=Arrays.asList(new CarDTO("VW GOL IV","KR12345",rentalClassDTO));

        //act
        List<CarDTO> resultCars=carService.readAll();

        //assert
        verify(carRepositoryMock);
        Assert.assertEquals(resultCars,expectedCars);
    }

}