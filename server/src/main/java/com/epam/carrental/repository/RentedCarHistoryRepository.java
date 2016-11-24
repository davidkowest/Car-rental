package com.epam.carrental.repository;

import com.epam.carrental.entity.RentedCarHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RentedCarHistoryRepository extends CrudRepository<RentedCarHistory, Long> {

    @Override
    List<RentedCarHistory> findAll();

}