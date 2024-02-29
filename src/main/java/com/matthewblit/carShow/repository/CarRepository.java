package com.matthewblit.carShow.repository;

import com.matthewblit.carShow.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long>{
}
