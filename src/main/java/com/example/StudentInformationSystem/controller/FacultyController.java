package com.example.StudentInformationSystem.controller;

import com.example.StudentInformationSystem.dto.FacultyDto;
import com.example.StudentInformationSystem.dto.MajorDto;
import com.example.StudentInformationSystem.service.FacultyService;
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

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDto> getFacultyById(@PathVariable Long id){

        return new ResponseEntity<FacultyDto>(facultyService.convertToFacultyDto(facultyService.getFacultyById(id)), HttpStatus.OK);

    }

    @GetMapping("/majors/{facultyId}")
    public ResponseEntity<List<MajorDto>>getMajors(@PathVariable Long facultyId){

        return new ResponseEntity<List<MajorDto>>(facultyService.getMajors(facultyId),HttpStatus.OK);

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
