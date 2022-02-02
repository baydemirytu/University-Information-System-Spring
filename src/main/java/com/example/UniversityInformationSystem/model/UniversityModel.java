package com.example.UniversityInformationSystem.model;

import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class UniversityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    @NotEmpty
    @NotNull
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private List<FacultyModel> facultyModelList;


}
