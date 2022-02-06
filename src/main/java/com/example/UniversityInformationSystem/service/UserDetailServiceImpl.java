package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final AcademicianService academicianService;

    private final AdminService adminService;

    private final StudentService studentService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        if(academicianService.getAcademicianByEmail(email)!=null){
            return AcademicianModel.create(academicianService.getAcademicianByEmail(email));
        }
        else if(studentService.getStudentByEmail(email)!=null){
            return StudentModel.create(studentService.getStudentByEmail(email));
        }
        else if(adminService.getAdminByEmail(email)!=null){

            return AdminModel.create(adminService.getAdminByEmail(email));
        }
        return null;
    }
}
