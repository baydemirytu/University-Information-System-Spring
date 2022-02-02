package com.example.StudentInformationSystem.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
public class FacultyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    @NotNull
    @NotEmpty
    private String name ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universityId")
    private UniversityModel universityModel;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MajorModel> majorModelList;


}
