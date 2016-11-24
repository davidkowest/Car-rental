package com.epam.carrental.repository;

import com.epam.carrental.entity.RentalClass;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RentalClassRepository extends CrudRepository<RentalClass, Long> {

    @Override
    List<RentalClass> findAll();

    RentalClass findByName(String name);
}