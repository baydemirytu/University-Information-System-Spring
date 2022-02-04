package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import com.example.UniversityInformationSystem.security.JwtUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final StudentService studentService;

    private final AcademicianService academicianService;

    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long id = Long.valueOf(username);

        if(id<100000){
            StudentModel studentModel = studentService.getStudentById(Long.valueOf(username));
            return JwtUserDetails.create(studentModel);
        }
        else if ( id>=100000 && id<200000){
            AcademicianModel academicianModel = academicianService.getAcademicianById(id);
            return JwtUserDetails.create(academicianModel);
        }
        else{
            AdminModel adminModel = adminService.getAdminById(id);
            return JwtUserDetails.create(adminModel);
        }

    }


}
