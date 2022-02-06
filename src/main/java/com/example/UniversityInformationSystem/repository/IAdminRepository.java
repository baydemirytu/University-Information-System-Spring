package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IAdminRepository extends JpaRepository<AdminModel,Long> {
    AdminModel findByEmail(String email);
}
