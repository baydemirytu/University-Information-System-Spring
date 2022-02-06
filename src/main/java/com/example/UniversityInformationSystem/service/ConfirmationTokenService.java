package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.model.ConfirmationTokenModel;
import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.repository.IConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final IConfirmationTokenRepository tokenRepository;

    @Transactional
    public void saveConfToken(ConfirmationTokenModel tokenModel){

        tokenRepository.save(tokenModel);

    }

    public ConfirmationTokenModel saveConfToken(EmailModel emailModel){

        String token = UUID.randomUUID().toString();

        ConfirmationTokenModel model = new ConfirmationTokenModel();
        model.setToken(token);
        model.setEmailModel(emailModel);
        return tokenRepository.save(model);


    }


    public ConfirmationTokenModel getTokenByToken(String token){

        return tokenRepository.findByToken(token);

    }

}
