package com.example.StudentInformationSystem.service;

import com.example.StudentInformationSystem.dto.AcademicianDto;
import com.example.StudentInformationSystem.model.AcademicianModel;
import com.example.StudentInformationSystem.repository.IAcademicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AcademicianService {

    private final IAcademicianRepository academicianRepository;

    private List<AcademicianDto> academicianDtoList;

    @Transactional
    public void addAcademician(AcademicianDto academicianDto) {

        AcademicianModel academicianModel = new AcademicianModel();
        academicianModel.setName(academicianDto.getName());
        academicianModel.setSurname(academicianDto.getSurname());
        academicianModel.setTitle(String.valueOf(academicianDto.getTitle()));
        academicianModel.setCourseModelList(null);
        academicianRepository.save(academicianModel);

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
        academicianDto.setTitle(String.valueOf(academicianModel.getTitle()));
        return academicianDto;

    }


    public AcademicianModel getAcademicianById(Long academicianId) {

        return academicianRepository.findById(academicianId).orElseThrow(
                () -> new RuntimeException("Academician can not found"));

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
}
