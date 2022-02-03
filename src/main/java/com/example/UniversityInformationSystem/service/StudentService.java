package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.StudentDto;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.repository.ICourseRepository;
import lombok.AllArgsConstructor;
import com.example.UniversityInformationSystem.model.StudentModel;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.UniversityInformationSystem.repository.IStudentRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@AllArgsConstructor
public class StudentService {

    private final IStudentRepository studentRepository;

    private final ICourseRepository courseRepository;

    private List<StudentDto> studentDtoList;

    private List<CourseModel> courseModelList;


    @Transactional
    public void addStudent (StudentDto studentDto){
        StudentModel studentModel = new StudentModel();
        studentModel.setName(studentDto.getName());
        studentModel.setSurname(studentDto.getSurname());
        studentModel.setMajorModel(null);
        studentModel.setCourseModelList(null);
        studentRepository.save(studentModel);

    }
    public List<StudentDto> getAllStudents(){
        studentDtoList.clear();

        studentRepository.findAll().forEach(item -> {

                studentDtoList.add(convertToStudentDto(item));
        });

        return studentDtoList;
    }


    public StudentModel getStudentById(Long id) {

        return studentRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Student can not found!"));


    }

    public StudentDto convertToStudentDto(StudentModel studentModel){

        StudentDto studentDto = new StudentDto();
        studentDto.setName(studentModel.getName());
        studentDto.setSurname(studentModel.getSurname());
        if(studentModel.getMajorModel() == null){
            studentDto.setMajorName("Does not have a major yet!");
        }else{
            studentDto.setMajorName(studentModel.getMajorModel().getName());
        }

        return  studentDto;
    }

    @Transactional
    public void deleteStudentById(Long studentId) {
        StudentModel studentModel = getStudentById(studentId);

        studentModel.getCourseModelList().forEach(courseModel -> {

            courseModel.getStudentModelList().remove(studentModel);

        });

        if(studentModel.getMajorModel()!=null){
            studentModel.getMajorModel().getStudentModelList().remove(studentModel);
            studentModel.setMajorModel(null);
        }


        studentRepository.deleteById(studentId);

    }

    public List<CourseModel> getTakenCourses(Long studentId) {
        courseModelList.clear();
        StudentModel studentModel = getStudentById(studentId);

        studentModel.getCourseModelList().forEach(courseModel -> {

            courseModelList.add(courseModel);

        });

        return courseModelList;

    }

    @Transactional
    public List<CourseModel> deleteCourseFromTakens(Long studentId, Long courseId){
        StudentModel studentModel = getStudentById(studentId);
        CourseModel courseModel = courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course can not found!")
        );

        studentModel.getCourseModelList().remove(courseModel);
        courseModel.getStudentModelList().remove(studentModel);
        studentRepository.save(studentModel);
        courseRepository.save(courseModel);

        return studentModel.getCourseModelList();

    }


}
