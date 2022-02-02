package com.example.StudentInformationSystem.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class AcademicianModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseModel> courseModelList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    private MajorModel majorModel;

    public enum  AcademicianType{
        PROFFESSOR,DOCENT,INSTRUCTOR,ASSISTANT
    }


}
