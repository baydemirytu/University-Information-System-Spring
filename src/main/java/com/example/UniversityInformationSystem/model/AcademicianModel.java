package com.example.UniversityInformationSystem.model;

import lombok.Data;

import javax.persistence.*;
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

    private String password;

    @NotNull
    @NotEmpty
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseModel> courseModelList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private MajorModel majorModel;




}
