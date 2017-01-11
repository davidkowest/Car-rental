package com.epam.carrental.rentals;

import com.epam.carrental.rentals.history.RentedCarHistory;

import java.time.ZonedDateTime;
import java.util.function.Predicate;

public class RentReturnDateFilter implements Predicate<RentedCarHistory> {
    private ZonedDateTime dateOfRent;
    private ZonedDateTime dateOfReturn;

    public RentReturnDateFilter(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {
        this.dateOfRent = dateOfRent;
        this.dateOfReturn = dateOfReturn;
    }

    @Override
    public boolean test(RentedCarHistory rentedCarHistory) {
        return rentedCarHistory.getStartDate().isBefore(dateOfReturn) && rentedCarHistory.getEndDate().isAfter(dateOfRent);
    }


}
