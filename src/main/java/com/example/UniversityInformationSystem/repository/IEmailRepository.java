package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmailRepository extends JpaRepository<EmailModel, Long> {

    EmailModel findByEmail(String email);


}
