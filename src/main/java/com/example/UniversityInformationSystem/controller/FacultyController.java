package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.response.AcademicianDto;
import com.example.UniversityInformationSystem.dto.response.FacultyDto;
import com.example.UniversityInformationSystem.dto.response.MajorDto;
import com.example.UniversityInformationSystem.dto.response.StudentDto;
import com.example.UniversityInformationSystem.service.FacultyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@AllArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }



    @GetMapping("/all")
    public ResponseEntity<List<FacultyDto>> getAllFaculties() {

        return new ResponseEntity<List<FacultyDto>>(facultyService.getAllFaculties(), HttpStatus.OK);

    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long facultyId){

        return new ResponseEntity<FacultyDto>(facultyService.convertToFacultyDto(facultyService.getFacultyById(facultyId)), HttpStatus.OK);

    }

    @GetMapping("/all/majors/{facultyId}")
    public ResponseEntity<List<MajorDto>>getMajors(@PathVariable Long facultyId){

        return new ResponseEntity<List<MajorDto>>(facultyService.getMajors(facultyId),HttpStatus.OK);

    }

    @GetMapping("/all/students/{facultyId}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable Long facultyId){

        return new ResponseEntity<List<StudentDto>>(facultyService.getAllStudents(facultyId),HttpStatus.OK);

    }

    @GetMapping("/all/academicians/{facultyId}")
    public ResponseEntity<List<AcademicianDto>> getAllAcademicians(@PathVariable Long facultyId){

        return new ResponseEntity<List<AcademicianDto>>(facultyService.getAllAcademicians(facultyId),HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addFaculty(@RequestBody FacultyDto facultyDto){
        facultyService.addFaculty(facultyDto);
        return new ResponseEntity<>(facultyDto.toString(), HttpStatus.OK);
    }

    @PostMapping("/add/major/{facultyId}-{majorId}")
    public ResponseEntity<FacultyDto> addMajorToFaculty(@PathVariable Long facultyId, @PathVariable Long majorId){

        return new ResponseEntity<FacultyDto>(facultyService.addMajorToFaculty(facultyId,majorId),HttpStatus.OK);

    }

}
