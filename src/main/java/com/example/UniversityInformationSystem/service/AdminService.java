package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.config.BeansConfig;
import com.example.UniversityInformationSystem.dto.request.AdminRegisterRequest;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.ConfirmationTokenModel;
import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.repository.IAdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminService{

    private IAdminRepository adminRepository;

    private BeansConfig beansConfig;

    private EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;

    @Transactional
    public void register(AdminRegisterRequest registerRequest){

        if(emailService.getEmailByEmail(registerRequest.getEmail())!=null){
            throw new AlreadyAddedException("The email is already exists!");
        }

        AdminModel adminModel = new AdminModel();
        adminModel.setName(registerRequest.getName());
        adminModel.setSurname(registerRequest.getSurname());
        adminModel.setEmail(registerRequest.getEmail());
        adminModel.setPassword(beansConfig.passwordEncoder().encode(registerRequest.getPassword()));
        adminModel = adminRepository.save(adminModel);


        EmailModel emailModel = emailService.saveEmailToRepo(adminModel);

        ConfirmationTokenModel confToken = confirmationTokenService.saveConfToken(emailModel);

        emailModel.setConfirmationTokenModel(confToken);

        emailModel = emailService.saveEmailToRepo(emailModel);

        String link = "http://localhost:8080/auth/confirm?token=" + confToken.getToken();

        emailService.sendEmail(adminModel.getEmail(),"Your UIS Activation email!",
                "Dear Admin(" + adminModel.getName() + " " + adminModel.getSurname() + ")," +
                        " Welcome to UIS!\nThis email contains your email verification link." +
                        " You will login the system via activating it. Do not share it!\n" +
                        "Your activation link: " + link);
    }


    public AdminModel getAdminById(Long adminId){

        return adminRepository.findById(adminId).orElseThrow(
                () -> new ModelNotFoundException("Admin can not found!"));

    }

    public AdminModel getAdminByEmail(String email) throws ModelNotFoundException{

        return adminRepository.findByEmail(email);

    }







    public void enableAdmin(EmailModel emailModel) {

        AdminModel adminModel = getAdminByEmail(emailModel.getEmail());
        adminModel.setEnabled(true);
        adminRepository.save(adminModel);

    }
}
