package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.AcademicianDto;
import com.example.UniversityInformationSystem.dto.CourseDto;
import com.example.UniversityInformationSystem.dto.StudentDto;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.model.StudentModel;
import com.example.UniversityInformationSystem.repository.ICourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class CourseService {

    private final ICourseRepository courseRepository;

    private final AcademicianService academicianService;

    private final StudentService studentService;

    private List<CourseDto> courseDtoList;

    private List<StudentDto> studentDtoList;

    @Transactional
    public void addCourse(CourseDto courseDto){
        CourseModel courseModel = new CourseModel();
        courseModel.setName(courseDto.getName());
        courseModel.setQuota(courseDto.getQuota());
        courseModel.setAcademicianModel(null);
        courseModel.setStudentModelList(null);
        courseRepository.save(courseModel);

    }


    public List<CourseDto> getAllCourses() {
        courseDtoList.clear();
        courseRepository.findAll().forEach(item -> {

            CourseDto courseDto = new CourseDto();
            courseDto.setName(item.getName());
            courseDto.setQuota(item.getQuota());
            if(item.getAcademicianModel() == null) {
                courseDto.setInstructorName(null);
            }else{
                courseDto.setInstructorName(item.getAcademicianModel().getName());
            }

            courseDtoList.add(courseDto);
        });

        return courseDtoList;

    }


    public CourseDto convertToCourseDto(CourseModel courseModel){

        CourseDto courseDto = new CourseDto();
        courseDto.setName(courseModel.getName());
        if(courseModel.getAcademicianModel() == null){
            courseDto.setInstructorName(null);
        }else {
            courseDto.setInstructorName(courseModel.getAcademicianModel().getName());
        }
        courseDto.setQuota(courseModel.getQuota());

        return courseDto;
    }


    public CourseModel getCourseById(Long courseId) {

        return courseRepository.findById(courseId).orElseThrow(
                () -> new RuntimeException("Course can not found!"));

    }


    @Transactional
    public CourseDto addAcademicianToCourse(Long courseId, Long academicianId) {

        AcademicianModel academicianModel = academicianService.getAcademicianById(academicianId);
        CourseModel courseModel = getCourseById(courseId);

        courseModel.setAcademicianModel(academicianModel);
        academicianModel.getCourseModelList().add(courseModel);
        courseRepository.save(courseModel);
        return convertToCourseDto(courseModel);
    }

    @Transactional
    public CourseDto addStudentToCourse(Long courseId, Long studentId) {
        AtomicBoolean isAlreadyAdded = new AtomicBoolean(false);
        StudentModel studentModel = studentService.getStudentById(studentId);
        CourseModel courseModel = getCourseById(courseId);

        courseModel.getStudentModelList().forEach(item -> {

            if(item.getStudentId()==studentId){
                isAlreadyAdded.set(true);
            }

        });

        if (isAlreadyAdded.get()){
            return convertToCourseDto(courseModel);
        }

        if(courseModel.getQuota()<=courseModel.getStudentModelList().size()){
            throw new RuntimeException("Quota is full! Increase the course quota!");

        }

        courseModel.getStudentModelList().add(studentModel);
        studentModel.getCourseModelList().add(courseModel);
        courseRepository.save(courseModel);
        return convertToCourseDto(courseModel);

    }
    public List<StudentDto> getAllStudents(Long courseId) {
        studentDtoList.clear();
        CourseModel courseModel = getCourseById(courseId);
        courseModel.getStudentModelList().forEach(item -> {

            studentDtoList.add(studentService.convertToStudentDto(item));

        });

        return studentDtoList;

    }

    public AcademicianDto getAcademician(Long courseId) {

        CourseModel courseModel = getCourseById(courseId);

        if(courseModel.getAcademicianModel()==null){
            return null;
        }

        return academicianService.convertToAcademicianDto(courseModel.getAcademicianModel());

    }

    @Transactional
    public void deleteCourseById(Long courseId) {
        CourseModel courseModel = getCourseById(courseId);

        if(courseModel.getMajorModel() != null){
            courseModel.getMajorModel().getCourseModelList().remove(courseModel);
            courseModel.setMajorModel(null);
        }

        courseModel.getStudentModelList().forEach(studentModel -> {

            studentModel.getCourseModelList().remove(courseModel);

        });

        if(courseModel.getAcademicianModel()!=null){
            courseModel.getAcademicianModel().getCourseModelList().remove(courseModel);
            courseModel.setAcademicianModel(null);
        }

        courseRepository.deleteById(courseId);

    }

    @Transactional
    public CourseDto updateCourseById(Long courseId, CourseDto courseDto) {

        CourseModel courseModel = getCourseById(courseId);

        courseModel.setName(courseDto.getName());
        if(courseDto.getQuota()>courseModel.getQuota()){
            courseModel.setQuota(courseDto.getQuota());
        }

        courseRepository.save(courseModel);
        return convertToCourseDto(courseModel);
    }
}
