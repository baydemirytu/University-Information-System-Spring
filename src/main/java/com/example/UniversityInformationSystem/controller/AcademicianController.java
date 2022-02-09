package com.example.UniversityInformationSystem.controller;

import com.example.UniversityInformationSystem.dto.response.AcademicianDto;
import com.example.UniversityInformationSystem.dto.response.CourseDto;
import com.example.UniversityInformationSystem.exception.LogicalMistakeException;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.security.JwtFilter;
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

    private final JwtFilter jwtFilter;

    @GetMapping
    public ResponseEntity<String> merhaba(){
        return new ResponseEntity<>("merhaba", HttpStatus.OK);
    }

    @GetMapping("/{academicianId}")
    public ResponseEntity<AcademicianDto> getAcademicianById(@PathVariable Long academicianId){


        return new ResponseEntity<AcademicianDto>(academicianService.convertToAcademicianDto(
                                                    academicianService.getAcademicianById(academicianId)),
                                                    HttpStatus.OK);

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




    @DeleteMapping("/delete/{academicianId}")
    public ResponseEntity<String> deleteAcademicianById(@PathVariable Long academicianId){
        academicianService.deleteAcademicianById(academicianId);
        return new ResponseEntity<String>("Academician deleted successfully!",HttpStatus.OK);

    }


    private boolean academicianValidator(Long academicianId){
        System.out.println(jwtFilter.getUser().getUsername());
        String role = jwtFilter.getUser().getAuthorities().toString();

        if(role.equals("[Academician]")){
            if (!academicianService.getAcademicianById(academicianId).getEmail().equals(jwtFilter.getUser().getUsername())){

                throw new LogicalMistakeException("You can look/edit details only for yourself!");

            }
        }
        return true;
    }


}
