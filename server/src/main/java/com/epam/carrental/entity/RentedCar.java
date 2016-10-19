package com.epam.carrental.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Car car;

    @ManyToOne
    private Customer customer;

    private ZonedDateTime dateOfRent;

    public RentedCar(Car car, Customer customer, ZonedDateTime dateOfRent) {
        this.car = car;
        this.customer = customer;
        this.dateOfRent = dateOfRent;
    }
}
