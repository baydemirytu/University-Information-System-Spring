package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.request.*;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import com.example.UniversityInformationSystem.repository.IAcademicianRepository;
import com.example.UniversityInformationSystem.repository.IAdminRepository;
import com.example.UniversityInformationSystem.repository.IStudentRepository;
import com.example.UniversityInformationSystem.security.JwtTokenProvider;
import com.example.UniversityInformationSystem.service.EmailService;
import com.example.UniversityInformationSystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NonUniqueResultException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private PasswordEncoder passwordEncoder;

    private final IStudentRepository studentRepository;

    private final IAcademicianRepository academicianRepository;

    private final IAdminRepository adminRepository;

    private final EmailService emailService;

    @PostMapping("/login/student")
    public String login(@RequestBody StudentLoginRequest studentLoginRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(studentLoginRequest.getId(),
                studentLoginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return "Bearer "+jwtToken;
    }

    @PostMapping("/register/student")
    @Transactional
    public ResponseEntity<String> register(@RequestBody StudentRegisterRequest registerRequest) {

        StudentModel studentModel = new StudentModel();
        studentModel.setName(registerRequest.getName());
        studentModel.setSurname(registerRequest.getSurname());
        studentModel.setEmail(registerRequest.getEmail());
        studentModel.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        studentModel.setMajorModel(null);
        studentModel.setCourseModelList(null);
        studentRepository.save(studentModel);

        try{
            studentModel = studentRepository.findByEmail(studentModel.getEmail());
        }catch (NonUniqueResultException e){
            throw new AlreadyAddedException("The email is already exists!");
        }


        emailService.sendEmail(studentModel.getEmail(),"Your UIS Student Id!",
                "Dear Student(" + studentModel.getName() + " " + studentModel.getSurname() + ")," +
                        "Welcome to UIS!\nThis email contains your secret Id." +
                        " You will login the system via using it. Do not share or forget it!\n" +
                        "Your Student id: " + studentModel.getStudentId());
        return new ResponseEntity<>("Student successfully registered! Please check your email immediately!", HttpStatus.CREATED);
    }


    @PostMapping("/register/academician")
    @Transactional
    public ResponseEntity<String> register(@RequestBody AcademicianRegisterRequest registerRequest){

        AcademicianModel academicianModel = new AcademicianModel();
        academicianModel.setName(registerRequest.getName());
        academicianModel.setSurname(registerRequest.getSurname());
        academicianModel.setTitle(registerRequest.getTitle());
        academicianModel.setEmail(registerRequest.getEmail());
        academicianModel.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        academicianModel.setMajorModel(null);
        academicianModel.setCourseModelList(null);
        academicianRepository.save(academicianModel);

        try{
            academicianModel = academicianRepository.findByEmail(academicianModel.getEmail());
        }catch (NonUniqueResultException e){
            throw new AlreadyAddedException("The email is already exists!");
        }

        emailService.sendEmail(academicianModel.getEmail(),"Your UIS Academician Id!",
                "Dear Academician(" + academicianModel.getName() + " " + academicianModel.getSurname() + ")," +
                        " Welcome to UIS!\nThis email contains your secret Id." +
                        " You will login the system via using it. Do not share or forget it!\n" +
                        "Your Student id: " + academicianModel.getAcademicianId());
        return new ResponseEntity<>("Academician successfully created! Please check your email immediately!",HttpStatus.CREATED);
    }

    @PostMapping("/login/academician")
    public String login(@RequestBody AcademicianLoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getId(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return "Bearer "+jwtToken;

    }

    @PostMapping("/secret/register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody AdminRegisterRequest registerRequest){
        AdminModel adminModel = new AdminModel();
        adminModel.setName(registerRequest.getName());
        adminModel.setSurname(registerRequest.getSurname());
        adminModel.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        adminRepository.save(adminModel);
        return new ResponseEntity<>("Admin successfully registered!",HttpStatus.CREATED);
    }

    @PostMapping("/secret/login/admin")
    public String login(@RequestBody AdminLoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getId(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        return "Bearer "+jwtToken;

    }
}
