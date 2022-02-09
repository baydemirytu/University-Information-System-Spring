package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.response.AcademicianDto;
import com.example.UniversityInformationSystem.dto.response.CourseDto;
import com.example.UniversityInformationSystem.dto.response.StudentDto;
import com.example.UniversityInformationSystem.exception.LogicalMistakeException;
import com.example.UniversityInformationSystem.security.JwtFilter;
import com.example.UniversityInformationSystem.service.AcademicianService;
import com.example.UniversityInformationSystem.service.CourseService;
import com.example.UniversityInformationSystem.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {


    private final CourseService courseService;

    private final JwtFilter jwtFilter;

    private final StudentService studentService;

    private final AcademicianService academicianService;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba",HttpStatus.OK);
    }

    @GetMapping("/academician/{courseId}")
    public ResponseEntity<AcademicianDto> getAcademician(@PathVariable Long courseId){

        return new ResponseEntity<AcademicianDto>(courseService.getAcademician(courseId),HttpStatus.OK);

    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId){

        return new ResponseEntity<CourseDto>(courseService.convertToCourseDto(courseService.getCourseById(courseId)),HttpStatus.OK);

    }


    @GetMapping("/all")
    public ResponseEntity<List<CourseDto>> getAllCourses() {

        return new ResponseEntity<List<CourseDto>>(courseService.getAllCourses(),HttpStatus.OK);

    }

    @GetMapping("/all/students/{courseId}")
    public ResponseEntity<List<StudentDto>> getAllStudents(@PathVariable Long courseId){

        return new ResponseEntity<List<StudentDto>>(courseService.getAllStudents(courseId),HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody CourseDto courseDto){
        courseService.addCourse(courseDto);
        return new ResponseEntity<>(courseDto.toString(), HttpStatus.OK);
    }

    @PostMapping("/add/academician/{courseId}-{academicianId}")
    public ResponseEntity<CourseDto> addAcademicianToCourse(@PathVariable Long courseId, @PathVariable Long academicianId){

        System.out.println(jwtFilter.getUser().getUsername());
        String role = jwtFilter.getUser().getAuthorities().toString();

        if(role.equals("[Academician]")){
            if (!academicianService.getAcademicianById(academicianId).getEmail()
                    .equals(jwtFilter.getUser().getUsername())){

                throw new LogicalMistakeException("You can add courses only for yourself!");

            }
        }

        return new ResponseEntity<CourseDto>(courseService.addAcademicianToCourse(courseId,academicianId),HttpStatus.OK);

    }

    @PostMapping("/add/student/{courseId}-{studentId}")
    public ResponseEntity<CourseDto> addStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId){

        System.out.println(jwtFilter.getUser().getUsername());
        String role = jwtFilter.getUser().getAuthorities().toString();

        if(role.equals("[Student]")){
            if (!studentService.getStudentById(studentId).getEmail()
                    .equals(jwtFilter.getUser().getUsername())){

                throw new LogicalMistakeException("You can add courses only for yourself!");

            }
        }

        return new ResponseEntity<CourseDto>(courseService.addStudentToCourse(courseId,studentId),HttpStatus.OK);

    }

    @DeleteMapping("/delete/{courseId}")
    public ResponseEntity<String> deleteCourseById(@PathVariable Long courseId){

        courseService.deleteCourseById(courseId);
        return new ResponseEntity<String>("Course deleted successfully!",HttpStatus.OK);

    }

    @PutMapping("/update/{courseId}")
    public ResponseEntity<CourseDto> updateCourseById(@PathVariable Long courseId, @RequestBody CourseDto courseDto){

        return new ResponseEntity<CourseDto>(courseService.updateCourseById(courseId,courseDto),HttpStatus.OK);

    }

}
