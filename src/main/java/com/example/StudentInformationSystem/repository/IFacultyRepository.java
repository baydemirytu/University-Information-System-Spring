package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacultyRepository extends JpaRepository<FacultyModel,Long> {

}
