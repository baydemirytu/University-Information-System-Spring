package com.example.StudentInformationSystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String surname;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "majorId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MajorModel majorModel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CourseModel> courseModelList;


}










