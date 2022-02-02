package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IStudentRepository extends JpaRepository<StudentModel,Long> {


}
