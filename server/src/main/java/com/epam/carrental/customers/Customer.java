package com.epam.carrental.customers;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(unique = true,nullable=false)
    private String email;

    public Customer(String name,String email){
        this.name=name;
        this.email=email;
    }
}

