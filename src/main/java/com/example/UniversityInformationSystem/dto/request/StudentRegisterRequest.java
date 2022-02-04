package com.example.UniversityInformationSystem.dto.request;

import lombok.Data;

@Data
public class StudentRegisterRequest {

    private String name;
    private String surname;
    private String password;

}
