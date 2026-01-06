package com.hostel.hostel.management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.http.ProblemDetail;

public class InvalidPasswordException extends ErrorResponseException {

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super(
                HttpStatus.BAD_REQUEST,
                createProblemDetail(),
                null
        );
    }

    private static ProblemDetail createProblemDetail() {
        ProblemDetail problemDetail =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Incorrect password");
        problemDetail.setDetail("The provided password is incorrect.");
        problemDetail.setType(
                java.net.URI.create("https://example.com/problem/invalid-password")
        );

        return problemDetail;
    }
}
