package com.matthewblit.carShow.service;

import com.matthewblit.carShow.dto.AuthRequest;
import com.matthewblit.carShow.dto.AuthResponse;
import com.matthewblit.carShow.entity.UserCredentials;
import com.matthewblit.carShow.repository.UserCredentialsRepository;
import com.matthewblit.carShow.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserCredentialsRepository userCredentialsRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

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
        UserCredentials userCredentials = userCredentialsRepository.findByEmail(authRequest.email())
                .orElseThrow();
        String jwt = jwtService.generateToken(userCredentials.getEmail());
        return new AuthResponse(jwt);
    }
}
