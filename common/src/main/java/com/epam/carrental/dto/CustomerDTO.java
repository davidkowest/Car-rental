package com.epam.carrental.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {

    private final String name;
    private final String email;

    public CustomerDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer {" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
