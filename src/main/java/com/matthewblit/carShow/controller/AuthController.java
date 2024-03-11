package com.matthewblit.carShow.controller;

import com.matthewblit.carShow.dto.AuthRequest;
import com.matthewblit.carShow.dto.AuthResponse;
import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.service.AuthService;
import com.matthewblit.carShow.service.UserCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    private final UserCredentialsService userCredentialsService;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, UserCredentialsService userCredentialsService) {
        this.authService = authService;
        this.userCredentialsService = userCredentialsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try{
            return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
        } catch (Exception e) {
            throw e;

        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCredentials userCredentials) {
        userCredentialsService.createUser(userCredentials);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
