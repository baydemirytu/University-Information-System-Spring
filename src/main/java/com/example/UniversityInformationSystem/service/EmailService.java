package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.model.roles.UserRole;
import com.example.UniversityInformationSystem.repository.IEmailRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;

    private final IEmailRepository emailRepository;

    public void sendEmail(String destination, String subject, String text){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("baydemir198@gmail.com");
        simpleMailMessage.setTo(destination);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);

    }

    public EmailModel getEmailByEmail(String email){
        return emailRepository.findByEmail(email);
    }

    @Transactional
    public void saveEmailToRepo(Long id, String email, UserRole role){

        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(id);
        emailModel.setEmail(email);
        emailModel.setUserRole(role);
        emailRepository.save(emailModel);

    }

}
