package com.example.StudentInformationSystem.service;

import com.example.StudentInformationSystem.dto.FacultyDto;
import com.example.StudentInformationSystem.dto.MajorDto;
import com.example.StudentInformationSystem.dto.StudentDto;
import com.example.StudentInformationSystem.model.FacultyModel;
import com.example.StudentInformationSystem.model.MajorModel;
import com.example.StudentInformationSystem.repository.IFacultyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class FacultyService {

    private final IFacultyRepository facultyRepository;

    private List<FacultyDto> facultyDtoList;

    private List<MajorDto> majorDtoList;

    private MajorService majorService;

    @Transactional
    public void addFaculty(FacultyDto facultyDto) {

        FacultyModel facultyModel = new FacultyModel();
        facultyModel.setName(facultyDto.getName());
        facultyModel.setMajorModelList(null);
        facultyModel.setUniversityModel(null);

        facultyRepository.save(facultyModel);

    }

    public List<FacultyDto> getAllFaculties() {
        facultyDtoList.clear();
        facultyRepository.findAll().forEach(item-> {

            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setName(item.getName());
            facultyDtoList.add(facultyDto);

        });
        return facultyDtoList;
    }

    public FacultyDto convertToFacultyDto(FacultyModel facultyModel){

        FacultyDto facultyDto = new FacultyDto();
        facultyDto.setName(facultyModel.getName());

        return facultyDto;

    }

    public FacultyModel getFacultyById(Long id) {

        return facultyRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("faculty can not found!"));


    }

    public List<MajorDto> getMajors(Long id){
        majorDtoList.clear();
        FacultyModel facultyModel = facultyRepository.findById(id).orElseThrow();
        facultyModel.getMajorModelList().forEach(item -> {

            majorDtoList.add(majorService.convertToMajorDto(item));

        });

        return majorDtoList;
    }

    @Transactional
    public FacultyDto addMajorToFaculty(Long facultyId, Long majorId) {
        AtomicBoolean isAlreadyAdded= new AtomicBoolean(false);
        MajorModel majorModel = majorService.getMajorById(majorId);
        FacultyModel facultyModel=getFacultyById(facultyId);

        facultyModel.getMajorModelList().forEach(item -> {

            if(item.getMajorId()==majorId){
                isAlreadyAdded.set(true);
            }

        });
        if(isAlreadyAdded.get()){
            return convertToFacultyDto(facultyModel);
        }

        facultyModel.getMajorModelList().add(majorModel);
        majorModel.setFacultyModel(facultyModel);
        facultyRepository.save(facultyModel);
        return convertToFacultyDto(facultyModel);

    }


}
