package com.matthewblit.carShow.service;

import com.matthewblit.carShow.dto.AuthRequest;
import com.matthewblit.carShow.dto.AuthResponse;
import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.exception.ResourceAccessException;
import com.matthewblit.carShow.repository.UserCredentialsRepository;
import com.matthewblit.carShow.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserCredentialsRepository userCredentialsRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    public AuthServiceImpl(UserCredentialsRepository userCredentialsRepository,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.email(),
                authRequest.password()
        ));
        logger.info(authRequest.toString());
        String jwt = jwtService.generateToken(authRequest.email());
        return new AuthResponse(jwt);
    }
}
