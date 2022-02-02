package com.example.StudentInformationSystem.controller;

import com.example.StudentInformationSystem.dto.FacultyDto;
import com.example.StudentInformationSystem.dto.UniversityDto;
import com.example.StudentInformationSystem.service.UniversityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/university")
@AllArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UniversityDto>> getAllUniversities(){

        return new ResponseEntity<List<UniversityDto>>(universityService.getAllUniversities(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id){

        return new ResponseEntity<UniversityDto>(universityService.convertToUniversityDto(universityService.getUniversityById(id)), HttpStatus.OK);

    }

    @GetMapping("/faculties/{uniId}")
    public ResponseEntity<List<FacultyDto>> getFaculties(@PathVariable Long uniId){

        return new ResponseEntity<List<FacultyDto>>(universityService.getFaculties(uniId),HttpStatus.OK);

    }



    @PostMapping("/add")
    public ResponseEntity<String> addUniversity (@RequestBody UniversityDto universityDto){

        universityService.addUniversity(universityDto);
        return new ResponseEntity<>(universityDto.toString(),HttpStatus.OK);

    }


    @PostMapping("/add/faculty/{uniId}-{facultyId}")
    public ResponseEntity<String> addFacultyToUniversity(@PathVariable Long uniId, @PathVariable Long facultyId){


        return new ResponseEntity<String>(universityService.addFacultyToUniversity(uniId,facultyId).toString(),HttpStatus.OK);

    }


}



