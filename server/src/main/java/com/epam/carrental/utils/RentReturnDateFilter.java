package com.epam.carrental.utils;

import com.epam.carrental.entity.RentedCarHistory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.function.Predicate;

@Component
public class RentReturnDateFilter {

    public  Predicate<RentedCarHistory> filterBy(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn){

        return rentedCarHistory -> (rentedCarHistory.getDateOfRent().isAfter(dateOfRent)
                || rentedCarHistory.getDateOfRent().equals(dateOfRent))
                && (rentedCarHistory.getDateOfReturn().isBefore(dateOfReturn)
                || (rentedCarHistory.getDateOfReturn().equals(dateOfReturn)));
    }
}
