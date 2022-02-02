package com.example.UniversityInformationSystem.service;

import com.example.UniversityInformationSystem.dto.FacultyDto;
import com.example.UniversityInformationSystem.dto.UniversityDto;
import com.example.UniversityInformationSystem.model.FacultyModel;
import com.example.UniversityInformationSystem.model.UniversityModel;
import com.example.UniversityInformationSystem.repository.IUniversityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class UniversityService {

    private final IUniversityRepository universityRepository;

    private List<UniversityDto> universityDtoList;

    private final FacultyService facultyService;

    private List<FacultyDto> facultyDtoList;

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
                 ()-> new RuntimeException("University can not found!"));
    }

    public List<FacultyDto> getFaculties(Long uniId) {
        facultyDtoList.clear();
        UniversityModel universityModel = universityRepository.findById(uniId).orElseThrow(() -> new RuntimeException("Uni can not found!"));
        universityModel.getFacultyModelList().forEach(item->{

            facultyDtoList.add(facultyService.convertToFacultyDto(item));
        });
        return facultyDtoList;
    }


    @Transactional
    public UniversityDto addFacultyToUniversity(Long uniId, Long facultyId) {
        AtomicBoolean isAlreadyAdded = new AtomicBoolean(false);
        FacultyModel facultyModel = facultyService.getFacultyById(facultyId);

        UniversityModel universityModel =  getUniversityById(uniId);

        universityModel.getFacultyModelList().forEach(item -> {

            if(item.getFacultyId()==facultyId){
                isAlreadyAdded.set(true);
                //Exception classını oluştur
            }
        });
        if(isAlreadyAdded.get() == true){
            return convertToUniversityDto(universityModel);
        }

        universityModel.getFacultyModelList().add(facultyModel);
        facultyModel.setUniversityModel(universityModel);
        universityRepository.save(universityModel);
        return convertToUniversityDto(universityModel);

    }


}
