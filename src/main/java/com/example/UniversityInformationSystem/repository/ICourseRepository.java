package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseModel,Long> {

}
