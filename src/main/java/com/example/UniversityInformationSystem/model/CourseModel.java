package com.example.UniversityInformationSystem.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private int quota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private MajorModel majorModel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academicianId")
    private AcademicianModel academicianModel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private List<StudentModel> studentModelList;

}










