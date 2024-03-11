package com.matthewblit.carShow.dto;

public record AuthRequest(
        String email,
        String password
) {
}
