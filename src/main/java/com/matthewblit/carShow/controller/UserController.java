package com.matthewblit.carShow.controller;

import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.service.UserCredentialsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserCredentialsService userCredentialsService;

    public UserController(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping("/")
    public ResponseEntity<?> postNewUser(UserCredentials userCredentials) {
        userCredentialsService.createUser(userCredentials);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }
}
