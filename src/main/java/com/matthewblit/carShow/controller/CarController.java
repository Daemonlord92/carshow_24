package com.matthewblit.carShow.controller;

import com.matthewblit.carShow.entity.Car;
import com.matthewblit.carShow.exception.ResourceAccessException;
import com.matthewblit.carShow.service.CarService;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Have the Controller and ResponseBody Annotation
@RestController
//Allows us to set the path for the endpoint start
@RequestMapping("/api/v1/car")
public class CarController {
    //Bring in the CarService object to be used by the controller
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/")
    //The PostMapping will mark this path expecting a POST http request from the front end.
    //It should include either a JSON body Object or ParameterURL to get the data
    public ResponseEntity<?> postNewCard(@RequestBody Car car) {
        carService.createCar(car);
        return new ResponseEntity<>("Car Created", HttpStatus.CREATED);
    }

    @GetMapping("/")
    //The GetMapping will mark this path for the GET http request
    //Most cases will not require a parameter but could require
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.FOUND);
    }

    //Old Way Robust and more control
    //@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    //New Way cleaner and more readable
    @PutMapping("/{id}")
    public ResponseEntity<Car> postUpdateCar(@PathVariable Long id, @RequestBody Car car) {
        return new ResponseEntity<>(carService.updateCar(id, car), HttpStatus.ACCEPTED);
    }
}
