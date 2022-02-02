package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.AcademicianModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcademicianRepository extends JpaRepository<AcademicianModel,Long> {

}
