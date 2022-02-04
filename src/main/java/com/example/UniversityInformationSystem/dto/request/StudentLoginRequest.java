package com.example.UniversityInformationSystem.dto.request;

import lombok.Data;

@Data
public class StudentLoginRequest {

    private String id;//Student id is used as username.
    private String password;
}
