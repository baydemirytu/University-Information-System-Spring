package com.example.StudentInformationSystem.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
public class MajorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    private int quota;

    @NotEmpty
    @NotNull
    private String location;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultyId")
    private FacultyModel facultyModel;


    @OneToMany(fetch = FetchType.LAZY)
    private List<StudentModel> studentModelList;


    @OneToMany(fetch = FetchType.LAZY)
    private List<AcademicianModel> academicianModelList;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseModel> courseModelList;


}
