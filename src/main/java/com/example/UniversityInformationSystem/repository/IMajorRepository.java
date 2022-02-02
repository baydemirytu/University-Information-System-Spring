package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.MajorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMajorRepository extends JpaRepository<MajorModel,Long> {

}
