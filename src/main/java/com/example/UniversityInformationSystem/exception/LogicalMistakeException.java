package com.example.UniversityInformationSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LogicalMistakeException extends RuntimeException{

    public LogicalMistakeException(String message) {
        super(message);
    }
}
