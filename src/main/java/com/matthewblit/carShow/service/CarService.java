package com.matthewblit.carShow.service;

import com.matthewblit.carShow.entity.Car;

import java.util.List;

public interface CarService {
    void createCar(Car car);
    List<Car> getAllCars();
    Car updateCar(Long id, Car car);
    void deleteCar(Long id);
}
