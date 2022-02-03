package com.example.UniversityInformationSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyAddedException extends RuntimeException{

    public AlreadyAddedException(String message) {
        super(message);
    }
}
