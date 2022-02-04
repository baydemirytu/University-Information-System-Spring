package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AdminModel;
import com.example.UniversityInformationSystem.repository.IAdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private IAdminRepository adminRepository;

    public AdminModel getAdminById(Long adminId){

        return adminRepository.findById(adminId).orElseThrow(
                () -> new ModelNotFoundException("Admin can not found!"));

    }


}
