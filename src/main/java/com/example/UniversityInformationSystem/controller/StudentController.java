package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.response.CourseDto;
import com.example.UniversityInformationSystem.dto.response.StudentDto;
import com.example.UniversityInformationSystem.exception.LogicalMistakeException;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.security.JwtFilter;
import com.example.UniversityInformationSystem.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.UniversityInformationSystem.service.StudentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private CourseService courseService;

    private List<CourseModel> courseModelList;

    private List<CourseDto> courseDtoList;

    private final JwtFilter jwtFilter;

    @GetMapping
    public ResponseEntity<String> merhaba(){

        return new ResponseEntity<>("merhaba",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents(){

        return new ResponseEntity<>(studentService.getAllStudents(),HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long studentId){

        studentValidator(studentId);

        return new ResponseEntity<StudentDto>(studentService.convertToStudentDto(studentService.getStudentById(studentId)), HttpStatus.OK);

    }

    @GetMapping("/courses/{studentId}")
    public ResponseEntity<List<CourseDto>> getTakenCourses(@PathVariable Long studentId){

        studentValidator(studentId);


        courseModelList.clear();
        courseDtoList.clear();
        courseModelList = studentService.getTakenCourses(studentId);

        courseModelList.forEach(courseModel -> {
            courseDtoList.add(courseService.convertToCourseDto(courseModel));
        });

        return new ResponseEntity<List<CourseDto>>(courseDtoList,HttpStatus.OK);

    }



    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<String> deleteStudentById(@PathVariable Long studentId){

        String role = jwtFilter.getUser().getAuthorities().toString();

        if(role.equals("[Student]")){
            throw new LogicalMistakeException("You can not delete your account! Please contact to admin.");
        }

        studentService.deleteStudentById(studentId);
        return new ResponseEntity<String>("Student deleted successfully!",HttpStatus.OK);

    }

    @DeleteMapping("/delete/course/{studentId}-{courseId}")
    public ResponseEntity<List<CourseDto>> deleteCourseFromTakens(@PathVariable Long studentId
                                                                , @PathVariable Long courseId){


        studentValidator(studentId);

        courseModelList.clear();
        courseDtoList.clear();
        courseModelList = studentService.deleteCourseFromTakens(studentId,courseId);

        courseModelList.forEach(courseModel -> {
            courseDtoList.add(courseService.convertToCourseDto(courseModel));
        });

        return new ResponseEntity<List<CourseDto>>(courseDtoList,HttpStatus.OK);
    }


    public boolean studentValidator(Long studentId){
        System.out.println(jwtFilter.getUser().getUsername());
        String role = jwtFilter.getUser().getAuthorities().toString();

        if(role.equals("[Student]")){
            if (!studentService.getStudentById(studentId).getEmail().equals(jwtFilter.getUser().getUsername())){

                throw new LogicalMistakeException("You can look/edit details only for yourself!");

            }
        }
        return true;
    }


}
