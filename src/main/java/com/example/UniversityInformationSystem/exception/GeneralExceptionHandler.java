package com.example.UniversityInformationSystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<?> modelNotFoundException(ModelNotFoundException e){

        Map<String,String> error = new HashMap<>();
        error.put("Error:",e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(AlreadyAddedException.class)
    public ResponseEntity<?> alreadyAddedException(AlreadyAddedException e){

        Map<String,String> error = new HashMap<>();
        error.put("Error:",e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(LogicalMistakeException.class)
    public ResponseEntity<?> logicalMistakeException(LogicalMistakeException e){

        Map<String,String> error = new HashMap<>();
        error.put("Error:",e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
