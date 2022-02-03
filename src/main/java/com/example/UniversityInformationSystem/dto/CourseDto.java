package com.example.UniversityInformationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private String name;
    private int quota;
    private int takenBy;
    private String instructorName;



}
