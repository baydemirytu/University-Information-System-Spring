package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;


@Repository
public interface IStudentRepository extends JpaRepository<StudentModel,Long> {


    StudentModel findByEmail(String email) throws NonUniqueResultException;

}
