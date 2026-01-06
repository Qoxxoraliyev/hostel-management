package com.hostel.hostel.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoginAlreadyUsedException extends ResponseStatusException {

    public LoginAlreadyUsedException() {
        super(HttpStatus.BAD_REQUEST, "Login already in use");
    }
}
