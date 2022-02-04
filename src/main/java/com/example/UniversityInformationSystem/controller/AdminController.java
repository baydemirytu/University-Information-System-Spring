package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.request.AdminRegisterRequest;
import com.example.UniversityInformationSystem.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secret/admin")
@AllArgsConstructor
public class AdminController {

    private AdminService adminService;




}
