package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.config.BeansConfig;
import com.example.UniversityInformationSystem.dto.request.AcademicianRegisterRequest;
import com.example.UniversityInformationSystem.dto.response.AcademicianDto;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.repository.IAcademicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AcademicianService implements UserDetailsService {

    private final IAcademicianRepository academicianRepository;

    private List<AcademicianDto> academicianDtoList;

    private List<CourseModel> courseModelList;

    private BeansConfig beansConfig;

    private EmailService emailService;



    @Transactional
    public void register(AcademicianRegisterRequest registerRequest){
        if(emailService.getEmailByEmail(registerRequest.getEmail())!=null){
            throw new AlreadyAddedException("The email is already exists!");
        }

        AcademicianModel academicianModel = new AcademicianModel();
        academicianModel.setName(registerRequest.getName());
        academicianModel.setSurname(registerRequest.getSurname());
        academicianModel.setEmail(registerRequest.getEmail());
        academicianModel.setPassword(beansConfig.passwordEncoder().encode(registerRequest.getPassword()));
        academicianModel.setMajorModel(null);
        academicianModel.setCourseModelList(null);
        academicianRepository.save(academicianModel);

        academicianModel = getAcademicianByEmail(registerRequest.getEmail());

        emailService.saveEmailToRepo(academicianModel.getAcademicianId(), academicianModel.getEmail(),
                academicianModel.getUserRole());


        emailService.sendEmail(academicianModel.getEmail(),"Your UIS Academician Id!",
                "Dear Academician(" + academicianModel.getName() + " " + academicianModel.getSurname() + ")," +
                        " Welcome to UIS!\nThis email contains your secret Id." +
                        " You will login the system via using it. Do not share or forget it!\n" +
                        "Your Academician id: " + academicianModel.getAcademicianId());
    }



    public List<AcademicianDto> getAllAcademicians() {
        academicianDtoList.clear();
        academicianRepository.findAll().forEach(item -> {

            academicianDtoList.add(convertToAcademicianDto(item));
        });

        return academicianDtoList;

    }


    public AcademicianDto convertToAcademicianDto(AcademicianModel academicianModel){

        AcademicianDto academicianDto = new AcademicianDto();
        academicianDto.setName(academicianModel.getName());
        academicianDto.setSurname(academicianModel.getSurname());
        return academicianDto;

    }


    public AcademicianModel getAcademicianById(Long academicianId) {

        return academicianRepository.findById(academicianId).orElseThrow(
                () -> new ModelNotFoundException("Academician can not found"));

    }

    @Transactional
    public void deleteAcademicianById(Long academicianId) {

        AcademicianModel academicianModel = getAcademicianById(academicianId);

        if(academicianModel.getMajorModel()!=null) {
            academicianModel.getMajorModel().getAcademicianModelList().remove(academicianModel);
            academicianModel.setMajorModel(null);
        }

        academicianModel.getCourseModelList().forEach(courseModel -> {

            courseModel.setAcademicianModel(null);

        });

        academicianRepository.deleteById(academicianId);

    }

    public List<CourseModel> getAllCourses(Long academicianId) {


        AcademicianModel academicianModel = getAcademicianById(academicianId);

        academicianModel.getCourseModelList().forEach(courseModel -> {

            courseModelList.add(courseModel);

        });

        return courseModelList;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AcademicianModel academicianModel = getAcademicianByEmail(email);
        return AcademicianModel.create(academicianModel);
    }

    public AcademicianModel getAcademicianByEmail(String email) {

        return academicianRepository.findByEmail(email);


    }
}
