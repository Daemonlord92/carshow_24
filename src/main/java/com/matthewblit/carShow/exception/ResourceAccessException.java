package com.matthewblit.carShow.exception;

import jakarta.persistence.EntityNotFoundException;

public class ResourceAccessException extends EntityNotFoundException {
    public ResourceAccessException(String message) {
        super(message);
    }
}
