package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IStudentRepository extends JpaRepository<StudentModel,Long> {


}
