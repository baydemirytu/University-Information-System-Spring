package com.example.UniversityInformationSystem.dto.request;

import lombok.Data;

@Data
public class AdminRegisterRequest {

    private String name;
    private String surname;
    private String password;

}
