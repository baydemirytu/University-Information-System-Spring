package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.UniversityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUniversityRepository extends JpaRepository<UniversityModel,Long> {

}
