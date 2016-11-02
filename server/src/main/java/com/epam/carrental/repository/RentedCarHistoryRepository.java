package com.epam.carrental.repository;

import com.epam.carrental.entity.RentedCarHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RentedCarHistoryRepository extends CrudRepository<RentedCarHistory, Long> {

    @Override
    List<RentedCarHistory> findAll();

}