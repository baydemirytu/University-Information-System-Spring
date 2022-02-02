package com.example.StudentInformationSystem.service;

import com.example.StudentInformationSystem.dto.AcademicianDto;
import com.example.StudentInformationSystem.dto.CourseDto;
import com.example.StudentInformationSystem.dto.MajorDto;
import com.example.StudentInformationSystem.dto.StudentDto;
import com.example.StudentInformationSystem.model.AcademicianModel;
import com.example.StudentInformationSystem.model.CourseModel;
import com.example.StudentInformationSystem.model.MajorModel;
import com.example.StudentInformationSystem.model.StudentModel;
import com.example.StudentInformationSystem.repository.IMajorRepository;
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

            MajorDto majorDto = new MajorDto();
            majorDto.setName(item.getName());
            majorDto.setQuota(item.getQuota());
            majorDto.setLocation(item.getLocation());
            majorDtoList.add(majorDto);
        });

        return majorDtoList;
    }


    public MajorDto convertToMajorDto(MajorModel majorModel){

        MajorDto majorDto = new MajorDto();
        majorDto.setName(majorModel.getName());
        majorDto.setQuota(majorModel.getQuota());
        majorDto.setLocation(majorModel.getLocation());
        return majorDto;


    }


    public MajorModel getMajorById(Long majorId) {

        return majorRepository.findById(majorId).orElseThrow(() -> new RuntimeException("Major can not found!"));

    }
    @Transactional
    public MajorDto addStudentToMajor(Long majorId, Long studentId) {
        AtomicBoolean isAlreadyAdded= new AtomicBoolean(false);
        StudentModel studentModel = studentService.getStudentById(studentId);
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getStudentModelList().forEach(item -> {

            if(item.getStudentId()== studentId){
                isAlreadyAdded.set(true);

            }

        });

        if(isAlreadyAdded.get()){
            return convertToMajorDto(majorModel);
        }

        majorModel.getStudentModelList().add(studentModel);
        studentModel.setMajorModel(majorModel);
        majorRepository.save(majorModel);
        return convertToMajorDto(majorModel);

    }

    @Transactional
    public MajorDto addAcademicianToMajor(Long majorId, Long academicianId) {
        AtomicBoolean isAlreadyAdded= new AtomicBoolean(false);
        AcademicianModel academicianModel = academicianService.getAcademicianById(academicianId);
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getAcademicianModelList().forEach(item -> {

            if(item.getAcademicianId()== academicianId){
                isAlreadyAdded.set(true);

            }

        });

        if(isAlreadyAdded.get()){
            return convertToMajorDto(majorModel);
        }

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
        AtomicBoolean isAlreadyAdded= new AtomicBoolean(false);
        CourseModel courseModel = courseService.getCourseById(courseId);
        MajorModel majorModel = getMajorById(majorId);

        majorModel.getCourseModelList().forEach(item -> {

            if(item.getCourseId()== courseId){
                isAlreadyAdded.set(true);

            }

        });

        if(isAlreadyAdded.get()){
            return convertToMajorDto(majorModel);
        }

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
