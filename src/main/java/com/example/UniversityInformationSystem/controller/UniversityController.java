package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.FacultyDto;
import com.example.UniversityInformationSystem.dto.MajorDto;
import com.example.UniversityInformationSystem.dto.StudentDto;
import com.example.UniversityInformationSystem.dto.UniversityDto;
import com.example.UniversityInformationSystem.service.UniversityService;
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

    @GetMapping("/{universityId}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long universityId){

        return new ResponseEntity<UniversityDto>(universityService.convertToUniversityDto(universityService.getUniversityById(universityId)), HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<UniversityDto>> getAllUniversities(){

        return new ResponseEntity<List<UniversityDto>>(universityService.getAllUniversities(),HttpStatus.OK);

    }



    @GetMapping("/all/faculties/{universityId}")
    public ResponseEntity<List<FacultyDto>> getFaculties(@PathVariable Long universityId){

        return new ResponseEntity<List<FacultyDto>>(universityService.getAllFaculties(universityId),HttpStatus.OK);

    }

    @GetMapping("/all/majors/{universityId}")
    public ResponseEntity<List<MajorDto>> getAllMajors(@PathVariable Long universityId){

        return new ResponseEntity<List<MajorDto>>(universityService.getAllMajors(universityId),HttpStatus.OK);

    }

    @GetMapping("/all/students/{universityId}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable Long universityId){

        return new ResponseEntity<List<StudentDto>>(universityService.getAllStudents(universityId),HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addUniversity (@RequestBody UniversityDto universityDto){

        universityService.addUniversity(universityDto);
        return new ResponseEntity<>(universityDto.toString(),HttpStatus.OK);

    }


    @PostMapping("/add/faculty/{universityId}-{facultyId}")
    public ResponseEntity<String> addFacultyToUniversity(@PathVariable Long universityId, @PathVariable Long facultyId){


        return new ResponseEntity<String>(universityService.addFacultyToUniversity(universityId,facultyId).toString(),HttpStatus.OK);

    }


}



