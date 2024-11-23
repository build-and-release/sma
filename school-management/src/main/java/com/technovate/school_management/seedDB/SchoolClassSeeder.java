package com.technovate.school_management.seedDB;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technovate.school_management.entity.SchoolClass;
import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.entity.enums.ClassEnum;
import com.technovate.school_management.entity.enums.ClassLevelEnum;
import com.technovate.school_management.model.SchoolClassSeedModel;
import com.technovate.school_management.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class SchoolClassSeeder implements CommandLineRunner {
    private final SchoolClassRepository schoolClassRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        List<SchoolClassSeedModel> schoolClassSeedModels = loadSchoolClassSeedModelsFromJson();

        for (SchoolClassSeedModel schoolClassSeedModel : schoolClassSeedModels) {
            Optional<SchoolClass> schoolClassOpt = schoolClassRepository.findByName(ClassEnum.valueOf(schoolClassSeedModel.getName().toUpperCase()));
//            check if that class has already been created an skip loop iteration
            if (schoolClassOpt.isPresent()) {
                System.out.println("School class already exist with name " + schoolClassSeedModel.getName().toUpperCase());
                continue;
            }
//            create class instead
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(schoolClassSeedModel.getId());
            schoolClass.setName(ClassEnum.valueOf(schoolClassSeedModel.getName().toUpperCase()));
            schoolClass.setLevel(ClassLevelEnum.valueOf(schoolClassSeedModel.getLevel().toUpperCase()));
            schoolClassRepository.save(schoolClass);
            System.out.println("Class " + schoolClass.getName() + " has been added");
//           check if there is a model whose nextClassId is this school class
            Optional<SchoolClassSeedModel> modelToUpdateNextClass = schoolClassSeedModels.stream()
                    .filter(model -> model.getNextClassId() != null && model.getNextClassId().equals(schoolClass.getId()))
                    .findFirst();

//            If no such class exists, continue
            if (modelToUpdateNextClass.isEmpty()) {
                continue;
            }
//            Find the class to update
            Optional<SchoolClass> schoolClassToUpdateOpt = schoolClassRepository.findById(modelToUpdateNextClass.get().getId());
            if (schoolClassToUpdateOpt.isEmpty()) {
                System.out.println("Class to update not found");
                continue;
            }
            SchoolClass schoolClassToUpdate = schoolClassToUpdateOpt.get();
            schoolClassToUpdate.setNextClass(schoolClass);
            schoolClassRepository.save(schoolClassToUpdate);
        }
    }

    private List<SchoolClassSeedModel> loadSchoolClassSeedModelsFromJson() throws IOException {
        // Load JSON from resources
        return objectMapper.readValue(
                new ClassPathResource("school-class.json").getInputStream(),
                new TypeReference<List<SchoolClassSeedModel>>() {}
        );
    }
}
