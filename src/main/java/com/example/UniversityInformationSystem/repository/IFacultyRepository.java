package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.FacultyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFacultyRepository extends JpaRepository<FacultyModel,Long> {

}
