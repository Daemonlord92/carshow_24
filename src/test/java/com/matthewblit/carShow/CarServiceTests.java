package com.matthewblit.carShow;

import com.matthewblit.carShow.entity.Car;
import com.matthewblit.carShow.repository.CarRepository;
import com.matthewblit.carShow.service.CarServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class CarServiceTests {
    @Mock
    //We are mocking the CarRepository for testing
    private CarRepository  carRepository;

    //We are creating an Autocloseable variable to store the MockitoAnnotations
    private AutoCloseable closeable;

    @BeforeEach
    public void setup(){
        //Here we are creating/ Setup the Mocks before each test for use
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        //Here we are closing the Mocks after each test, so data is not shared
        // by the other tests
        closeable.close();
    }

    @InjectMocks
    //Here we pass the MockObjects to the Service Layer Like a Bean.
    private CarServiceImpl carService;

    @Test
    public void carServiceGetAllCarsSuccessShouldReturnList() {
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.addAll(
                Arrays.asList(
                        new Car(),
                        new Car()
                )
        );

        Mockito.when(carRepository.findAll()).thenReturn(expectedCars);

        List<Car> actualCars = carService.getAllCars();

        assertThat(actualCars).isEqualTo(expectedCars);
    }

    @Test
    public void testGetCars() {
        List<Car> actualCars = carService.getAllCars();

        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void carServiceSuccessShouldSaveCar() {
        Car car = new Car();

        carService.createCar(car);

        verify(carRepository, times(1)).save(car);
    }
}
