package com.example.StudentInformationSystem.repository;

import com.example.StudentInformationSystem.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<CourseModel,Long> {

}
