package com.epam.carrental.rentals;


import com.epam.carrental.cars.Car;
import com.epam.carrental.customers.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@Check(constraints = "endDate > startDate")
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Car car;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private ZonedDateTime startDate;

    @Column(nullable = false)
    private ZonedDateTime endDate;

    public RentedCar(Car car, Customer customer, ZonedDateTime startDate, ZonedDateTime plannedDateOfReturn) {
        this.car = car;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = plannedDateOfReturn;
    }
}
