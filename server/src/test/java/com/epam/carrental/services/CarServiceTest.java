package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.repository.CarRepository;
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
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        CarServiceImpl carServiceimpl = new CarServiceImpl();
        this.carRepositoryMock=createStrictMock(CarRepository.class);
        carServiceimpl.modelMapper=modelMapper;
        carServiceimpl.carRepository=carRepositoryMock;
        this.carService=carServiceimpl;
    }

    @Test
    public void carCreateTest() {
        //arrange
        CarDTO carDTO=new CarDTO("VW GOL IV","KR12345");
        Car car=modelMapper.map(carDTO, Car.class);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(null);
        EasyMock.expect(carRepositoryMock.save(car)).andReturn(car);
        replay(carRepositoryMock);

        //act
        carService.create(carDTO);

        //assert
        verify(carRepositoryMock);
    }

    @Test(expectedExceptions = { IllegalArgumentException.class})
    public void theSameCarCreationTwiceTest() {
        //arrange
        CarDTO carDTO=new CarDTO("VW GOL IV","KR12345");
        Car car=modelMapper.map(carDTO, Car.class);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(null);
        EasyMock.expect(carRepositoryMock.save(car)).andReturn(car);
        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(carDTO.getRegistrationNumber())).andReturn(car);
        EasyMock.expect(carRepositoryMock.save(car)).andThrow(new IllegalArgumentException());
        replay(carRepositoryMock);

        //act
        carService.create(carDTO);
        carService.create(carDTO);

        //assert
        verify(carRepositoryMock);
    }

    @Test
    public void readAllCarsTest() {
        //arrange
        EasyMock.expect(carRepositoryMock.findAll()).andReturn(Arrays.asList(new Car("VW GOL IV","KR12345")));
        replay(carRepositoryMock);
        List<CarDTO> expectedCars=Arrays.asList(new CarDTO("VW GOL IV","KR12345"));

        //act
        List<CarDTO> resultCars=carService.readAll();

        //assert
        verify(carRepositoryMock);
        Assert.assertEquals(resultCars,expectedCars);
    }

}