package com.epam.carrental.repository;

import com.epam.carrental.entity.RentedCarHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public interface RentedCarHistoryRepository extends CrudRepository<RentedCarHistory, Long> {
    @Override
    List<RentedCarHistory> findAll();

    default List<RentedCarHistory> findByDateOfRentAndDateOfReturn(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {
        return findAll().stream()
                .filter(rentedCarHistory -> rentedCarHistory.getDateOfRent().isAfter(dateOfRent) && rentedCarHistory.getDateOfReturn().isBefore(dateOfReturn))
                .collect(Collectors.toList());
    }
}