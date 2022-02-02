package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.MajorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMajorRepository extends JpaRepository<MajorModel,Long> {

}
