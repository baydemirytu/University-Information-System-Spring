package com.example.UniversityInformationSystem.dto.request;

import lombok.Data;

@Data
public class AcademicianRegisterRequest {

    private String name;
    private String surname;
    private String password;
    private String title;

}
