package com.epam.carrental.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames ={ "car_id", "customer_id","dateOfRent","plannedDateOfReturn" })})
@Check(constraints = "plannedDateOfReturn > dateOfRent")
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private ZonedDateTime dateOfRent;

    @Column(nullable = false)
    private ZonedDateTime plannedDateOfReturn;

    public RentedCar(Car car, Customer customer, ZonedDateTime dateOfRent,ZonedDateTime plannedDateOfReturn) {
        this.car = car;
        this.customer = customer;
        this.dateOfRent = dateOfRent;
        this.plannedDateOfReturn=plannedDateOfReturn;
    }
}
