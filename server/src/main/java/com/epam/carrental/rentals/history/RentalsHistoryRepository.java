package com.epam.carrental.rentals.history;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentalsHistoryRepository extends CrudRepository<RentedCarHistory, Long> {

    @Override
    List<RentedCarHistory> findAll();

}