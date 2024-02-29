package com.matthewblit.carShow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String make;
    private String model;
    private String vin;

    @ManyToOne
    @JsonIgnore
    private Owner owner;

    public Car() {
    }

    public Car(String make, String model, String vin) {
        this.make = make;
        this.model = model;
        this.vin = vin;
    }

    public Car(String make, String model, String vin, Owner owner) {
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", vin='" + vin + '\'' +
                ", owner=" + owner+
                '}';
    }
}
