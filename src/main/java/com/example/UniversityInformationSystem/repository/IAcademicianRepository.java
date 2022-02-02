package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAcademicianRepository extends JpaRepository<AcademicianModel,Long> {

}
