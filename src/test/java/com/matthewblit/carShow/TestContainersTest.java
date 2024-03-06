package com.matthewblit.carShow;

import com.matthewblit.carShow.controller.CarController;
import com.matthewblit.carShow.entity.Car;
import com.matthewblit.carShow.service.CarService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class TestContainersTest {
    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CarService carService;

    @Autowired
    CarController carController;

    @Container
    private static final MySQLContainer<?> mySqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"))
            //First Number is the Docker Container Port, Second is the Port on your computer where Docker is going to map it
            .withExposedPorts(3306, 3322)
            .withDatabaseName("carshowdb")
            .withUsername("billybobjoe")
            .withPassword("Gudmord92!");

    @DynamicPropertySource
    //Setting up the connection for Docker to recognize the connections
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySqlContainer::getUsername);
        registry.add("spring.datasource.password", mySqlContainer::getPassword);
    }

    @BeforeAll
    public static void beforeAll() { mySqlContainer.start(); }

    @AfterAll
    public static void afterAll() { mySqlContainer.stop(); }

    @Test
    void testContainerizedDatabase() {
        jdbcTemplate.execute("USE carshowdb");

        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM car", Integer.class);
        Assertions.assertEquals(4, count);
    }

    @Test
    void carControllerShouldReturnList() {
        ResponseEntity<List<Car>> response = carController.getCars();

        Assertions.assertEquals(302, response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody());
    }
}
