package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.UniversityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUniversityRepository extends JpaRepository<UniversityModel,Long> {

}
