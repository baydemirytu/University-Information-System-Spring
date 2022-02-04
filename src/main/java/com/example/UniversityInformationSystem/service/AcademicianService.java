package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.response.AcademicianDto;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.AcademicianModel;
import com.example.UniversityInformationSystem.model.CourseModel;
import com.example.UniversityInformationSystem.repository.IAcademicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AcademicianService {

    private final IAcademicianRepository academicianRepository;

    private List<AcademicianDto> academicianDtoList;

    private List<CourseModel> courseModelList;


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
        academicianDto.setTitle(String.valueOf(academicianModel.getTitle()));
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
}
