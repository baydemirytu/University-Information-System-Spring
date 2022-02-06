package com.example.UniversityInformationSystem.repository;

import com.example.UniversityInformationSystem.model.ConfirmationTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConfirmationTokenRepository extends JpaRepository<ConfirmationTokenModel, Long> {
    ConfirmationTokenModel findByToken(String token);
}
