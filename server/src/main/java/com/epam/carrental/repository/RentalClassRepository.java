package com.epam.carrental.repository;

import com.epam.carrental.entity.RentalClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RentalClassRepository extends CrudRepository<RentalClass, Long> {

    @Override
    List<RentalClass> findAll();

    RentalClass findByName(String name);
}