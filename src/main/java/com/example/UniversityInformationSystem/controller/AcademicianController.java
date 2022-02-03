package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.AcademicianDto;
import com.example.UniversityInformationSystem.dto.CourseDto;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.service.AcademicianService;
import com.example.UniversityInformationSystem.service.CourseService;
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

    private final CourseService courseService;

    private List<CourseDto> courseDtoList;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<AcademicianDto>> getAllAcademicians(){

        return new ResponseEntity<List<AcademicianDto>>(academicianService.getAllAcademicians(), HttpStatus.OK);

    }

    @GetMapping("/all/courses/{academicianId}")
    public ResponseEntity<List<CourseDto>> getAllCourses(@PathVariable Long academicianId){
        courseDtoList.clear();
        List<CourseModel> courseModelList = academicianService.getAllCourses(academicianId);

        courseModelList.forEach(courseModel -> {

            courseDtoList.add(courseService.convertToCourseDto(courseModel));

        });

        return new ResponseEntity<List<CourseDto>>(courseDtoList,HttpStatus.OK);

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
