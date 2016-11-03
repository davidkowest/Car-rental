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
    public void testCarHistoryMatchingConditions(RentedCarHistory history, boolean expected) {
        //arrange
        ZonedDateTime filterDateOfRent = getTime("2015-08-15T10:00:00Z");
        ZonedDateTime filterDateOfReturn = getTime("2015-08-30T10:10:00Z");

        RentReturnDateFilter rentReturnDateFilter = new RentReturnDateFilter(filterDateOfRent,filterDateOfReturn);

        //act
        boolean rentedCarHistoryPredicate = rentReturnDateFilter.test(history);

        //assert
        assertEquals(rentedCarHistoryPredicate,expected, "Test history with dateOfRent:  "+ history.getDateOfRent()+ "and dateOfReturn: "+history.getDateOfReturn());
    }

    @DataProvider
    public Object[][] pickDates() {
        return new Object[][]{
                {getHistory(getTime("2015-08-20T10:10:00Z"), getTime("2015-08-25T10:00:00Z")),true},// rented after picked dateOfRent, return before picked dateOfReturn
                {getHistory(getTime("2015-08-10T10:00:00Z"), getTime("2015-08-25T10:00:00Z")),true},// rented before picked dateOfRent, return before picked dateOfReturn
                {getHistory(getTime("2015-08-25T10:00:00Z"), getTime("2015-09-10T10:00:00Z")),true},// rented before picked dateOfReturn, return after picked dateOfReturn
                {getHistory(getTime("2015-07-10T10:00:00Z"), getTime("2015-09-10T10:00:00Z")),true}, // rented before picked dateOfRent, return after picked dateOfReturn
                {getHistory(getTime("2015-08-10T10:00:00Z"), getTime("2015-08-12T10:00:00Z")),false}, // rented and return before picked period
                {getHistory(getTime("2015-09-10T10:10:00Z"), getTime("2015-09-25T10:00:00Z")),false}, // rented and return after picked period
                {getHistory(getTime("2015-08-15T10:00:00Z"), getTime("2015-08-30T10:10:00Z")),true}, // rented and return dates are like in filter datePicker

        };
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

    private RentedCarHistory getHistory(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {
        return new RentedCarHistory(
                new Car("Opel Vectra", "o095ey"),
                new Customer("White Rabbit", "white@gmail.com"),dateOfRent,dateOfReturn);
    }
}
