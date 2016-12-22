package com.epam.carrental.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"car_id", "customer_id", "startDate", "endDate"})})
@Check(constraints = "endDate > startDate")
public class RentedCarHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "ID")
    private Car car;

    @ManyToOne
    private Customer customer;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public RentedCarHistory(Car car, Customer customer, ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn) {
        this.car = car;
        this.customer = customer;
        this.startDate = dateOfRent;
        this.endDate = dateOfReturn;
    }
}