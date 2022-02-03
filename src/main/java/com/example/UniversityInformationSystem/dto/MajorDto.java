package com.example.UniversityInformationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorDto {

    private String name;
    private int quota;
    private int studentNumber;
    private String location;

}
