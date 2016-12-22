package com.epam.carrental.services;

import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.entity.RentedCarHistory;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentalClassRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;


public class HistoryStatisticsServiceImplTest {

    @InjectMocks
    private HistoryStatisticsServiceImpl historyStatisticsService = new HistoryStatisticsServiceImpl();
    @Mock
    private CarRepository carRepositoryMock;
    @Mock
    private RentalClassRepository rentalClassRepositoryMock;

    @BeforeTest
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        when(carRepositoryMock.findAll()).thenReturn(getCars());
        when(rentalClassRepositoryMock.findAll()).thenReturn(getClasses());
    }

    @Test
    public void testGetCarAmountPerClass() {
        //arrange
        Map<String, Long> expected = new HashMap<>();
        expected.put("economy", 3L);
        expected.put("compact", 2L);
        expected.put("fullsize", 0L);
        //act
        Map<String, Long> result = historyStatisticsService.getCarAmountPerClass();
        //assert
        assertEquals(result, expected);
    }

    @Test(dataProvider = "getHistoriesToCut")
    public void testCutOverlappingDate(RentedCarHistory rentedCarHistory, ZonedDateTime expectedStart, ZonedDateTime expectedEnd) {
        // arrange
        ZonedDateTime startDate = ZonedDateTime.parse("2015-09-12T15:00:00Z");
        ZonedDateTime endDate = ZonedDateTime.parse("2015-09-16T15:00:00Z");
        //act
        RentedCarHistory result = historyStatisticsService.cutOverlappingDate(rentedCarHistory, startDate, endDate);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.getStartDate(), expectedStart, "expectedStart: " + expectedStart + " actual: " + result.getStartDate());
        sa.assertEquals(result.getEndDate(), expectedEnd, "expectedEnd: " + expectedEnd + " actual: " + result.getEndDate());
        sa.assertAll();
    }

    @DataProvider
    public Object[][] getHistoriesToCut() {
        return new Object[][]{
                {new RentedCarHistory(new Car(), new Customer(),
                        getTime("2015-09-10T15:00:00Z"), getTime("2015-09-17T20:00:00Z")), getTime("2015-09-12T15:00:00Z"), getTime("2015-09-16T15:00:00Z")},
                {new RentedCarHistory(new Car(), new Customer(),
                        getTime("2015-09-10T15:00:00Z"), getTime("2015-09-14T20:00:00Z")), getTime("2015-09-12T15:00:00Z"), getTime("2015-09-14T20:00:00Z")},
                {new RentedCarHistory(new Car(), new Customer(),
                        getTime("2015-09-13T15:00:00Z"), getTime("2015-09-17T20:00:00Z")), getTime("2015-09-13T15:00:00Z"), getTime("2015-09-16T15:00:00Z")}
        };
    }

    private List<Car> getCars() {
        List<RentalClass> rentalClasses = getClasses();
        return Arrays.asList(new Car("Nissan 200SX", "KR34567", rentalClasses.get(0)),
                new Car("Chrysler Concorde", "WAR34567", rentalClasses.get(0)),
                new Car("Honda del Sol", "RZE34567", rentalClasses.get(0)),
                new Car("Jeep Cherokee", "WAR12345", rentalClasses.get(1)),
                new Car("Kia Sportage", "RZE12345", rentalClasses.get(1)));
    }

    private List<RentalClass> getClasses() {
        return Arrays.asList(
                new RentalClass("economy", 2.45f),
                new RentalClass("compact", 3.4f),
                new RentalClass("fullsize", 4.7f));
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }
}