package com.matthewblit.carShow.security;

import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.service.UserCredentialsService;

import java.util.Optional;

public class JwtService {
    private final String SECRET = "a9f8893f39102da418c104819105d7ae604ff2b3";

    private final UserCredentialsService userCredentialsService;

    public JwtService(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    public String generateToken(String email) {
        Optional<UserCredentials> userCredentials = userCredentialsService.getUserByEmail(email);
    }
}
