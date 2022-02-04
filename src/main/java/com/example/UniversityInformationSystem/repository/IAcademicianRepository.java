package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NonUniqueResultException;

@Repository
public interface IAcademicianRepository extends JpaRepository<AcademicianModel,Long> {

    AcademicianModel findByEmail(String email) throws NonUniqueResultException;
}
