package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.AcademicianDto;
import com.example.UniversityInformationSystem.service.AcademicianService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academician")
@AllArgsConstructor
public class AcademicianController {

    private final AcademicianService academicianService;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<AcademicianDto>> getAllAcademicians(){

        return new ResponseEntity<List<AcademicianDto>>(academicianService.getAllAcademicians(), HttpStatus.OK);

    }



    @PostMapping("/add")
    public ResponseEntity<String> addAcademician (@RequestBody AcademicianDto academicianDto){

        academicianService.addAcademician(academicianDto);
        return new ResponseEntity<>(academicianDto.toString(),HttpStatus.OK);

    }

    @DeleteMapping("/delete/{academicianId}")
    public ResponseEntity<String> deleteAcademicianById(@PathVariable Long academicianId){
        academicianService.deleteAcademicianById(academicianId);
        return new ResponseEntity<String>("Academician deleted successfully!",HttpStatus.OK);

    }


}
