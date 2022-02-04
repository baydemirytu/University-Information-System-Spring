package com.example.UniversityInformationSystem.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class AcademicianModel {

    @Id
    @SequenceGenerator(name = "academician_id_seq", initialValue = 100000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academician_id_seq")
    private Long academicianId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;

    @NotNull
    @NotEmpty
    private String title;

    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;



    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseModel> courseModelList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private MajorModel majorModel;




}
