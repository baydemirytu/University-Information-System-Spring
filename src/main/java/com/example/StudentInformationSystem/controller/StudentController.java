package com.example.StudentInformationSystem.controller;

import com.example.StudentInformationSystem.dto.CourseDto;
import com.example.StudentInformationSystem.dto.StudentDto;
import com.example.StudentInformationSystem.model.CourseModel;
import com.example.StudentInformationSystem.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.StudentInformationSystem.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private CourseService courseService;

    private List<CourseModel> courseModelList;

    private List<CourseDto> courseDtoList;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents(){

        return new ResponseEntity<>(studentService.getAllStudents(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id){

        return new ResponseEntity<StudentDto>(studentService.convertToStudentDto(studentService.getStudentById(id)), HttpStatus.OK);

    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<List<CourseDto>> getTakenCourses(@PathVariable Long studentId){
        courseModelList.clear();
        courseDtoList.clear();
        courseModelList = studentService.getTakenCourses(studentId);

        courseModelList.forEach(courseModel -> {
            courseDtoList.add(courseService.convertToCourseDto(courseModel));
        });

        return new ResponseEntity<List<CourseDto>>(courseDtoList,HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody StudentDto studentDto){
        studentService.addStudent(studentDto);
        return new ResponseEntity<>(studentDto.toString(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long studentId){

        studentService.deleteStudentById(studentId);
        return new ResponseEntity<String>("Student deleted successfully!",HttpStatus.OK);

    }

}
