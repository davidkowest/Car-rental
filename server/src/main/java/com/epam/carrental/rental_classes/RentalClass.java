package com.epam.carrental.rental_classes;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class RentalClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable=false)
    private String name;

    @Column(nullable=false)
    private float hourlyRate;

    public RentalClass(String name, float hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }
}