package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.config.BeansConfig;
import com.example.UniversityInformationSystem.dto.request.StudentRegisterRequest;
import com.example.UniversityInformationSystem.dto.response.StudentDto;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.ConfirmationTokenModel;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.model.EmailModel;
import com.example.UniversityInformationSystem.repository.ICourseRepository;
import lombok.AllArgsConstructor;
import com.example.UniversityInformationSystem.model.StudentModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.UniversityInformationSystem.repository.IStudentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class StudentService {

    private final IStudentRepository studentRepository;

    private final ICourseRepository courseRepository;

    private List<StudentDto> studentDtoList;

    private List<CourseModel> courseModelList;

    private BeansConfig beansConfig;

    private EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;


    @Transactional
    public void registerStudent(StudentRegisterRequest registerRequest){
        if(emailService.getEmailByEmail(registerRequest.getEmail())!=null){
            throw new AlreadyAddedException("The email is already exists!");
        }
        StudentModel studentModel = new StudentModel();
        studentModel.setName(registerRequest.getName());
        studentModel.setSurname(registerRequest.getSurname());
        studentModel.setEmail(registerRequest.getEmail());
        studentModel.setPassword(beansConfig.passwordEncoder().encode(registerRequest.getPassword()));
        studentModel.setMajorModel(null);
        studentModel.setCourseModelList(null);

        studentModel = studentRepository.save(studentModel);

        EmailModel emailModel = emailService.saveEmailToRepo(studentModel);


        ConfirmationTokenModel confToken = confirmationTokenService.saveConfToken(emailModel);


        emailModel.setConfirmationTokenModel(confToken);


        emailModel = emailService.saveEmailToRepo(emailModel);

        String link = "https://university-information-system.herokuapp.com/auth/confirm?token=" + confToken.getToken();

        emailService.sendEmail(studentModel.getEmail(),"Your UIS activating email!",
                "Dear Student(" + studentModel.getName() + " " + studentModel.getSurname() + ")," +
                        "Welcome to UIS!\nThis email contains your email verification link." +
                        " You will login the system via activating it. Do not share it!\n" +
                        "Your activation link: " + link);

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
                ()-> new ModelNotFoundException("Student can not found!"));


    }

    public StudentModel getStudentByEmail(String email){

        return studentRepository.findByEmail(email);


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

        emailService.deleteEmailByEmail(studentModel.getEmail());

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
                () -> new ModelNotFoundException("Course can not found!")
        );

        studentModel.getCourseModelList().remove(courseModel);
        courseModel.getStudentModelList().remove(studentModel);
        studentRepository.save(studentModel);
        courseRepository.save(courseModel);

        return studentModel.getCourseModelList();

    }



    public void enableStudent(EmailModel emailModel) {

        StudentModel studentModel = getStudentByEmail(emailModel.getEmail());
        studentModel.setEnabled(true);
        studentRepository.save(studentModel);

    }
}
