package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.response.FacultyDto;
import com.example.UniversityInformationSystem.dto.response.MajorDto;
import com.example.UniversityInformationSystem.dto.response.StudentDto;
import com.example.UniversityInformationSystem.dto.response.UniversityDto;
import com.example.UniversityInformationSystem.exception.AlreadyAddedException;
import com.example.UniversityInformationSystem.exception.ModelNotFoundException;
import com.example.UniversityInformationSystem.model.FacultyModel;
import com.example.UniversityInformationSystem.model.UniversityModel;
import com.example.UniversityInformationSystem.repository.IUniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversityService {

    private final IUniversityRepository universityRepository;

    private List<UniversityDto> universityDtoList;

    private final FacultyService facultyService;

    private final StudentService studentService;

    private final MajorService majorService;

    private List<FacultyDto> facultyDtoList;

    private List<MajorDto> majorDtoList;

    private List<StudentDto> studentDtoList;

    @Transactional
    public void addUniversity(UniversityDto universityDto) {

        UniversityModel universityModel = new UniversityModel();
        universityModel.setName(universityDto.getName());
        universityModel.setFacultyModelList(null);

        universityRepository.save(universityModel);

    }


    public List<UniversityDto> getAllUniversities() {
        universityDtoList.clear();
        universityRepository.findAll().forEach(item->{

            UniversityDto universityDto = new UniversityDto();
            universityDto.setName(item.getName());
            universityDtoList.add(universityDto);
        });
        return universityDtoList;
    }

    public UniversityDto convertToUniversityDto(UniversityModel universityModel){

        UniversityDto universityDto = new UniversityDto();
        universityDto.setName(universityModel.getName());


        return universityDto;

    }

    public UniversityModel getUniversityById(Long id) {

         return universityRepository.findById(id).orElseThrow(
                 ()-> new ModelNotFoundException("University can not found!"));
    }

    public List<FacultyDto> getAllFaculties(Long universityId) {
        facultyDtoList.clear();
        UniversityModel universityModel = getUniversityById(universityId);
        universityModel.getFacultyModelList().forEach(item->{

            facultyDtoList.add(facultyService.convertToFacultyDto(item));
        });
        return facultyDtoList;
    }


    @Transactional
    public UniversityDto addFacultyToUniversity(Long uniId, Long facultyId) {
        FacultyModel facultyModel = facultyService.getFacultyById(facultyId);

        UniversityModel universityModel =  getUniversityById(uniId);

        universityModel.getFacultyModelList().forEach(item -> {

            if(item.getFacultyId()==facultyId){
                throw new AlreadyAddedException("Faculty is already added to University!");
            }
        });

        universityModel.getFacultyModelList().add(facultyModel);
        facultyModel.setUniversityModel(universityModel);
        universityRepository.save(universityModel);
        return convertToUniversityDto(universityModel);

    }

    public List<MajorDto> getAllMajors(Long universityId){
        majorDtoList.clear();
        UniversityModel universityModel = getUniversityById(universityId);

        universityModel.getFacultyModelList().forEach(facultyModel -> {

            facultyModel.getMajorModelList().forEach(majorModel -> {

                majorDtoList.add(majorService.convertToMajorDto(majorModel));

            });

        });
        return majorDtoList;
    }

    public List<StudentDto> getAllStudents(Long universityId) {
        studentDtoList.clear();
        UniversityModel universityModel = getUniversityById(universityId);

        universityModel.getFacultyModelList().forEach(facultyModel -> {

            facultyModel.getMajorModelList().forEach(majorModel -> {

                majorModel.getStudentModelList().forEach(studentModel -> {

                    studentDtoList.add(studentService.convertToStudentDto(studentModel));

                });

            });

        });

        return studentDtoList;
    }
}
