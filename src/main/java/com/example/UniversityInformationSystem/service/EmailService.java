package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.model.StudentModel;
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
    public EmailModel saveEmailToRepo(StudentModel studentModel){

        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(studentModel.getStudentId());
        emailModel.setEmail(studentModel.getEmail());
        emailModel.setUserRole(studentModel.getUserRole());
        return emailRepository.save(emailModel);

    }


    @Transactional
    public EmailModel saveEmailToRepo(AdminModel adminModel){

        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(adminModel.getAdminId());
        emailModel.setEmail(adminModel.getEmail());
        emailModel.setUserRole(adminModel.getUserRole());
        return emailRepository.save(emailModel);

    }


    @Transactional
    public EmailModel saveEmailToRepo(AcademicianModel academicianModel){

        EmailModel emailModel = new EmailModel();
        emailModel.setUserId(academicianModel.getAcademicianId());
        emailModel.setEmail(academicianModel.getEmail());
        emailModel.setUserRole(academicianModel.getUserRole());
        return emailRepository.save(emailModel);

    }


    @Transactional
    public EmailModel saveEmailToRepo(EmailModel emailModel){


        return emailRepository.save(emailModel);

    }





}
