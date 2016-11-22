package com.epam.carrental.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(unique = true, nullable = false)
    private String registrationNumber;
    @ManyToOne
    @JoinColumn(nullable = false)
    private RentalClass rentalClass;

    public Car(String model,String registrationNumber,RentalClass rentalClass){
        this.model=model;
        this.registrationNumber=registrationNumber;
        this.rentalClass=rentalClass;
    }
}
