package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.AcademicianDto;
import com.example.UniversityInformationSystem.dto.CourseDto;
import com.example.UniversityInformationSystem.dto.MajorDto;
import com.example.UniversityInformationSystem.dto.StudentDto;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.LogicalMistakeException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.model.MajorModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import com.example.UniversityInformationSystem.repository.IMajorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class MajorService {

    private final IMajorRepository majorRepository;

    private final StudentService studentService;

    private final AcademicianService academicianService;

    private final CourseService courseService;

    private List<StudentDto> studentDtoList;

    private List<AcademicianDto> academicianDtoList;

    private List<MajorDto> majorDtoList;

    private List<CourseDto> courseDtoList;

    @Transactional
    public void addMajor(MajorDto majorDto){

        MajorModel majorModel = new MajorModel();
        majorModel.setName(majorDto.getName());
        majorModel.setQuota(majorDto.getQuota());
        majorModel.setLocation(majorDto.getLocation());
        majorModel.setFacultyModel(null);
        majorModel.setAcademicianModelList(null);
        majorModel.setStudentModelList(null);
        majorRepository.save(majorModel);
    }


    public List<MajorDto> getAllMajors() {
        majorDtoList.clear();

        majorRepository.findAll().forEach(item -> {

            majorDtoList.add(convertToMajorDto(item));

        });

        return majorDtoList;
    }


    public MajorDto convertToMajorDto(MajorModel majorModel){

        MajorDto majorDto = new MajorDto();
        majorDto.setName(majorModel.getName());
        majorDto.setQuota(majorModel.getQuota());
        majorDto.setStudentNumber(majorModel.getStudentModelList().size());
        majorDto.setLocation(majorModel.getLocation());
        return majorDto;


    }


    public MajorModel getMajorById(Long majorId) {

        return majorRepository.findById(majorId).orElseThrow(
                () -> new ModelNotFoundException("Major can not found!"));

    }

    @Transactional
    public MajorDto addStudentToMajor(Long majorId, Long studentId) {

        StudentModel studentModel = studentService.getStudentById(studentId);
        MajorModel majorModel = getMajorById(majorId);

        if(studentModel.getMajorModel()!=null){
            throw new AlreadyAddedException("Student is already added to a major!");
        }

        if(majorModel.getStudentModelList().size()>=majorModel.getQuota()){
            throw new LogicalMistakeException("Quota of major is full!");
        }

        majorModel.getStudentModelList().forEach(item -> {

            if(item.getStudentId()== studentId){
                throw new AlreadyAddedException("Student is already added to Major!");

            }

        });

        majorModel.getStudentModelList().add(studentModel);
        studentModel.setMajorModel(majorModel);
        majorRepository.save(majorModel);
        return convertToMajorDto(majorModel);

    }

    @Transactional
    public MajorDto addAcademicianToMajor(Long majorId, Long academicianId) {

        AcademicianModel academicianModel = academicianService.getAcademicianById(academicianId);
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getAcademicianModelList().forEach(item -> {

            if(item.getAcademicianId()== academicianId){
                throw new AlreadyAddedException("Academician is already added to Major!");

            }

        });


        majorModel.getAcademicianModelList().add(academicianModel);
        academicianModel.setMajorModel(majorModel);
        majorRepository.save(majorModel);
        return convertToMajorDto(majorModel);

    }

    public List<StudentDto> getAllStudents(Long majorId) {
        studentDtoList.clear();
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getStudentModelList().forEach(item -> {

            studentDtoList.add(studentService.convertToStudentDto(item));

        });

        return studentDtoList;

    }

    public List<AcademicianDto> getAllAcademicians(Long majorId) {
        academicianDtoList.clear();

        MajorModel majorModel = getMajorById(majorId);

        majorModel.getAcademicianModelList().forEach(item -> {

            academicianDtoList.add(academicianService.convertToAcademicianDto(item));

        });

        return academicianDtoList;

    }

    @Transactional
    public MajorDto addCourseToMajor(Long majorId, Long courseId) {
        CourseModel courseModel = courseService.getCourseById(courseId);
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getCourseModelList().forEach(item -> {

            if(item.getCourseId()== courseId){
                throw new AlreadyAddedException("Course is already added to Major!");

            }

        });


        majorModel.getCourseModelList().add(courseModel);
        courseModel.setMajorModel(majorModel);
        majorRepository.save(majorModel);
        return convertToMajorDto(majorModel);

    }

    public List<CourseDto> getAllCourses(Long majorId) {
        courseDtoList.clear();

        MajorModel majorModel = getMajorById(majorId);

        majorModel.getCourseModelList().forEach(item -> {

            courseDtoList.add(courseService.convertToCourseDto(item));

        });

        return courseDtoList;

    }

}
