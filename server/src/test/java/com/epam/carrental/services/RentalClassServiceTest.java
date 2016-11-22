package com.epam.carrental.services;

import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.repository.RentalClassRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

public class RentalClassServiceTest {

    private RentalClassService rentalClassService;
    private RentalClassRepository rentalClassRepositoryMock;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        RentalClassServiceImpl rentalClassServiceImpl = new RentalClassServiceImpl();
        this.rentalClassRepositoryMock=createStrictMock(RentalClassRepository.class);
        rentalClassServiceImpl.modelMapper=modelMapper;
        rentalClassServiceImpl.rentalClassRepository=rentalClassRepositoryMock;
        this.rentalClassService=rentalClassServiceImpl;
    }

    @Test
    public void rentalClassCreateTest() {
        //arrange
        RentalClassDTO rentalClassDTO=new RentalClassDTO("Economy",2.45f);
        RentalClass rentalClass=modelMapper.map(rentalClassDTO, RentalClass.class);

        EasyMock.expect(rentalClassRepositoryMock.save(rentalClass)).andReturn(rentalClass);
        replay(rentalClassRepositoryMock);

        //act
        rentalClassService.create(rentalClassDTO);
        //assert
        verify(rentalClassRepositoryMock);
    }

    @Test
    public void readAllRentalClassesTest()  {
        //arrange
        EasyMock.expect(rentalClassRepositoryMock.findAll()).andReturn(Arrays.asList(new RentalClass("Economy",2.45f)));
        replay(rentalClassRepositoryMock);
        List<RentalClassDTO> expectedRentalClasses=Arrays.asList(new RentalClassDTO("Economy",2.45f));

        //act
        List<RentalClassDTO> resultRentalClasses=rentalClassService.readAll();

        //assert
        verify(rentalClassRepositoryMock);
        Assert.assertEquals(resultRentalClasses,expectedRentalClasses);
    }

}
