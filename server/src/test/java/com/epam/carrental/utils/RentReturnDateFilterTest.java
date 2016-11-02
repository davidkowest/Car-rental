package com.epam.carrental.utils;

import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCarHistory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.util.function.Predicate;

import static org.testng.Assert.assertEquals;

public class RentReturnDateFilterTest {

    @Test(dataProvider = "pickDates")
    public void testCarHistoryMatchingConditions(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn, boolean expected) {
        //arrange
        RentReturnDateFilter rentReturnDateFilter = new RentReturnDateFilter();
        RentedCarHistory rentedCarHistory = getRentedCarHistory();

        //act
        Predicate<RentedCarHistory> rentedCarHistoryPredicate = rentReturnDateFilter.filterBy(dateOfRent, dateOfReturn);

        //assert
        assertEquals(rentedCarHistoryPredicate.test(rentedCarHistory),expected, "Test "+dateOfRent+ " as rent and " + dateOfReturn+ " as return date");
    }

    @DataProvider
    public Object[][] pickDates() {
        return new Object[][]{
                {getTime("2015-08-16T15:23:01Z"), getTime("2015-08-25T15:23:01Z"),true},
                {getTime("2015-08-16T15:22:01Z"), getTime("2015-08-25T15:24:01Z"),true},
                {getTime("2015-08-16T15:23:00Z"), getTime("2015-08-25T15:23:02Z"),true},
                {getTime("2015-07-10T10:00:00Z"), getTime("2015-09-23T11:00:00Z"),true},
                {getTime("2015-08-16T15:23:02Z"), getTime("2015-08-25T15:23:00Z"),false}, // frames' dates are only one second differ from history's dates
                {getTime("2015-09-10T15:22:00Z"), getTime("2015-09-25T15:24:01Z"),false}, // car is rented and returned after picked dates
                {getTime("2015-07-15T15:22:00Z"), getTime("2015-08-22T10:10:00Z"),false}, // car is rented before picked date though returned before picked date
                {getTime("2015-08-18T10:20:00Z"), getTime("2015-08-26T10:10:00Z"),false}, // car is rented after picked date but not returned till picked date
                {getTime("2015-08-26T10:10:00Z"), getTime("2015-08-18T10:20:00Z"),false}, // switch dateOfRent and dateOfReturn
        };
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

    private RentedCarHistory getRentedCarHistory() {
        return new RentedCarHistory(
                new Car("Opel Vectra", "o095ey"),
                new Customer("White Rabbit", "white@gmail.com"),
                getTime("2015-08-16T15:23:01Z"),
                getTime("2015-08-25T15:23:01Z"));
    }
}
