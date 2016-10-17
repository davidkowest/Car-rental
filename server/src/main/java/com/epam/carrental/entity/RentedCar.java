package com.epam.carrental.entity;


import javax.persistence.*;

@Entity
public class RentedCar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    Car car;

    @ManyToOne
    Customer customer;

    public RentedCar() {
    }

    public RentedCar(Car car, Customer customer) {
        this.car=car;
        this.customer=customer;
    }
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

}
