package com.matthewblit.carShow.service;

import com.matthewblit.carShow.dto.AuthRequest;
import com.matthewblit.carShow.dto.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest authRequest);
}
