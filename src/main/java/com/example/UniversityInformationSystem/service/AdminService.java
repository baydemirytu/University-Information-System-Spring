package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.config.BeansConfig;
import com.example.UniversityInformationSystem.dto.request.AdminRegisterRequest;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AdminModel;
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
public class AdminService implements UserDetailsService {

    private IAdminRepository adminRepository;

    private BeansConfig beansConfig;

    private EmailService emailService;



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
        adminRepository.save(adminModel);

        adminModel=getAdminByEmail(registerRequest.getEmail());

        EmailModel emailModel = emailService.saveEmailToRepo(adminModel);

        emailService.sendEmail(adminModel.getEmail(),"Your UIS Admin Id!",
                "Dear Admin(" + adminModel.getName() + " " + adminModel.getSurname() + ")," +
                        "Welcome to UIS!\nThis email contains your secret Id." +
                        " You will login the system via using it. Do not share or forget it!\n" +
                        "Your Admin id: " + adminModel.getAdminId());
    }


    public AdminModel getAdminById(Long adminId){

        return adminRepository.findById(adminId).orElseThrow(
                () -> new ModelNotFoundException("Admin can not found!"));

    }

    public AdminModel getAdminByEmail(String email) throws ModelNotFoundException{

        return adminRepository.findByEmail(email);

    }


    public boolean hasAdminByEmail(String email){

        if(getAdminByEmail(email)==null){
            return false;
        }
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminModel adminModel = getAdminByEmail(email);
        return AdminModel.create(adminModel);
    }


}
