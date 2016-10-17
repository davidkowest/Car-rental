package com.epam.carrental.entity;

import javax.persistence.*;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String model;
    @Column(unique = true, nullable = false)
    private String registrationNumber;

    public Car() {
    }

    public Car(String model, String registrationNumber) {
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != null ? !id.equals(car.id) : car.id != null) return false;
        return model != null ? model.equals(car.model) : car.model == null &&
                (registrationNumber != null ? registrationNumber.equals(car.registrationNumber) : car.registrationNumber == null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (registrationNumber != null ? registrationNumber.hashCode() : 0);
        return result;
    }
}
