package com.epam.carrental.rental_classes;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface RentalClassRepository extends CrudRepository<RentalClass, Long> {

    @Override
    List<RentalClass> findAll();

    RentalClass findByName(String name);
}