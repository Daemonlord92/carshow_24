package com.matthewblit.carShow.service;

import com.matthewblit.carShow.entity.Car;
import com.matthewblit.carShow.exception.ResourceAccessException;
import com.matthewblit.carShow.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void createCar(Car car) {
        carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car updateCar(Long id, Car car) {
        //Retrieving the Car Data from the database
        Optional<Car> optionalCar = carRepository.findById(id);
        //Checking to make sure that Car exists in the database
        if(optionalCar.isEmpty()) throw new ResourceAccessException("Car not found!");
        //Creating an If Tree to see if properties in the object needs to be replaced
        if(!optionalCar.get().getMake().equals(car.getMake())){
            //if false then replace the object property with the new property.
            optionalCar.get().setMake(car.getMake());
        }
        if(!optionalCar.get().getModel().equals(car.getModel())){
            optionalCar.get().setModel(car.getModel());
        }
        if(!optionalCar.get().getVin().equals(car.getVin())){
            optionalCar.get().setVin(car.getVin());
        }
        //Saving the updates to the car
        carRepository.save(optionalCar.get());
        //returning the car to the controller
        return optionalCar.get();
    }

    @Override
    public void deleteCar(Long id) {
        //This setup will either return the car object or it will throw an exception
        Car optionalCar = carRepository.findById(id)
                //here is the exception setup, so instead of an if statement it's one line of code
                .orElseThrow(() -> new ResourceAccessException("Car not Found"));
        //then we delete by using the object
        carRepository.delete(optionalCar);
        //carRepository.deleteById(id);
    }


}
