package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.AcademicianDto;
import com.example.UniversityInformationSystem.dto.FacultyDto;
import com.example.UniversityInformationSystem.dto.MajorDto;
import com.example.UniversityInformationSystem.dto.StudentDto;
import com.example.UniversityInformationSystem.model.FacultyModel;
import com.example.UniversityInformationSystem.model.MajorModel;
import com.example.UniversityInformationSystem.repository.IFacultyRepository;
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

    private List<StudentDto> studentDtoList;

    private List<AcademicianDto> academicianDtoList;

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

    public List<StudentDto> getAllStudents(Long facultyId){
        studentDtoList.clear();
        FacultyModel facultyModel = getFacultyById(facultyId);

        facultyModel.getMajorModelList().forEach(majorModel -> {

            majorService.getAllStudents(majorModel.getMajorId()).forEach(studentDto -> {

                studentDtoList.add(studentDto);

            });

        });
        return studentDtoList;
    }

    public List<AcademicianDto> getAllAcademicians(Long facultyId){
        academicianDtoList.clear();
        FacultyModel facultyModel = getFacultyById(facultyId);

        facultyModel.getMajorModelList().forEach(majorModel -> {

            majorService.getAllAcademicians(majorModel.getMajorId()).forEach(academicianDto -> {

                academicianDtoList.add(academicianDto);

            });

        });
        return academicianDtoList;
    }

}
