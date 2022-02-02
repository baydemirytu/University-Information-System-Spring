package com.example.UniversityInformationSystem.exception;


import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class exception extends RuntimeException{

    public exception (String s){
        super(s);
    }

}
