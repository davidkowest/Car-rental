package com.epam.carrental.repository;

import com.epam.carrental.entity.BookedCar;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookedCarRepository extends CrudRepository<BookedCar, Long> {

    @Override
    List<BookedCar> findAll();
}