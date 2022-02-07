package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.request.*;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.ConfirmationTokenModel;
import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.model.roles.UserRole;
import com.example.UniversityInformationSystem.security.JwtTokenProvider;
import com.example.UniversityInformationSystem.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private final StudentService studentService;

    private final AcademicianService academicianService;

    private final AdminService adminService;

    private final EmailService emailService;

    private final ConfirmationTokenService tokenService;


    @GetMapping("/confirm")
    @Transactional
    public ResponseEntity<String> confirmAccount(@RequestParam(name = "token") String token){

        ConfirmationTokenModel confToken = tokenService.getTokenByToken(token);
        EmailModel emailModel = emailService.getEmailByEmail(confToken.getEmailModel().getEmail());
        UserRole role = emailModel.getUserRole();
        if(role==UserRole.Student){
            studentService.enableStudent(emailModel);
        }
        else if (role == UserRole.Academician){
            academicianService.enableAcademician(emailModel);
        }
        else if (role == UserRole.Admin){
            adminService.enableAdmin(emailModel);
        }
        return new ResponseEntity<String>(emailModel.getEmail()+" is verified. You con login now!",HttpStatus.OK);

    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){

        if(emailService.getEmailByEmail(loginRequest.getEmail())==null){
            throw new ModelNotFoundException("This email is not registered!");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String role = auth.getAuthorities().toString();


        if(role.equals("[Student]")){
            return "Bearer " + jwtTokenProvider.studentJwtToken(auth);
        }
        else if(role.equals("["+UserRole.Admin+"]")){
            return "Bearer " + jwtTokenProvider.adminJwtToken(auth);
        }
        else if(role.equals("["+UserRole.Academician+"]")){
            return "Bearer " + jwtTokenProvider.academicianJwtToken(auth);
        }
        return "null";

    }

    @PostMapping("/register/student")
    @Transactional
    public ResponseEntity<String> register(@RequestBody StudentRegisterRequest registerRequest) {

        studentService.registerStudent(registerRequest);

        return new ResponseEntity<>("Student successfully registered! Please check your email immediately!", HttpStatus.CREATED);
    }

    @PostMapping("/register/academician")
    @Transactional
    public ResponseEntity<String> register(@RequestBody AcademicianRegisterRequest registerRequest){

        academicianService.register(registerRequest);
        return new ResponseEntity<>("Academician successfully created! Please check your email immediately!",HttpStatus.CREATED);
    }

    @PostMapping("/secret/register/admin")
    @Transactional
    public ResponseEntity<String> register(@RequestBody AdminRegisterRequest registerRequest){
        adminService.register(registerRequest);
        return new ResponseEntity<>("Admin successfully registered! Please check your email immediately!",HttpStatus.CREATED);
    }


}
