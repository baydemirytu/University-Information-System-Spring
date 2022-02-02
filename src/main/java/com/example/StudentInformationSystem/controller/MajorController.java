package com.example.StudentInformationSystem.controller;

import com.example.StudentInformationSystem.dto.*;
import com.example.StudentInformationSystem.service.MajorService;
import com.example.StudentInformationSystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/major")
@AllArgsConstructor
public class MajorController {

    private final MajorService majorService;

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MajorDto>> getAllMajors () {

        return new ResponseEntity<List<MajorDto>>(majorService.getAllMajors(), HttpStatus.OK);

    }
    @GetMapping("/{majorId}")
    public ResponseEntity<MajorDto> getMajorById(@PathVariable Long majorId){

        return new ResponseEntity<MajorDto>(majorService.convertToMajorDto(majorService.getMajorById(majorId)),HttpStatus.OK);

    }

    @GetMapping("/all/students/{majorId}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable Long majorId){

        return new ResponseEntity<List<StudentDto>>(majorService.getAllStudents(majorId),HttpStatus.OK);

    }

    @GetMapping("/all/academicians/{majorId}")
    public ResponseEntity<List<AcademicianDto>> getAllAcademicians(@PathVariable Long majorId){

        return new ResponseEntity<List<AcademicianDto>>(majorService.getAllAcademicians(majorId),HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addMajor(@RequestBody MajorDto majorDto){
        majorService.addMajor(majorDto);
        return new ResponseEntity<>(majorDto.toString(),
                HttpStatus.OK);
    }

    @PostMapping("/add/student/{majorId}-{studentId}")
    public ResponseEntity<MajorDto> addStudentToMajor(@PathVariable Long majorId, @PathVariable Long studentId){

        return new ResponseEntity<MajorDto>(majorService.addStudentToMajor(majorId,studentId),HttpStatus.OK);

    }

    @PostMapping("/add/academician/{majorId}-{academicianId}")
    public ResponseEntity<MajorDto> addAcademicianToMajor(@PathVariable Long majorId, @PathVariable Long academicianId){

        return new ResponseEntity<MajorDto>(majorService.addAcademicianToMajor(majorId,academicianId),HttpStatus.OK);

    }

    @PostMapping("/add/course/{majorId}-{courseId}")
    public ResponseEntity<MajorDto> addCourseToMajor(@PathVariable Long majorId, @PathVariable Long courseId){

        return new ResponseEntity<MajorDto>(majorService.addCourseToMajor(majorId,courseId),HttpStatus.OK);

    }

    @GetMapping("/all/courses/{majorId}")
    public ResponseEntity<List<CourseDto>> getAllCourses(@PathVariable Long majorId){

        return new ResponseEntity<List<CourseDto>>(majorService.getAllCourses(majorId),HttpStatus.OK);

    }


}
