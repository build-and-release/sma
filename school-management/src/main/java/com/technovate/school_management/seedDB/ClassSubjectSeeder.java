package com.technovate.school_management.seedDB;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technovate.school_management.entity.ClassSubject;
import com.technovate.school_management.entity.SchoolClass;
import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.model.ClassSubjectSeedData;
import com.technovate.school_management.repository.ClassSubjectRepository;
import com.technovate.school_management.repository.SchoolClassRepository;
import com.technovate.school_management.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClassSubjectSeeder implements CommandLineRunner  {
    private final ClassSubjectRepository classSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository schoolClassRepository;

    private List<ClassSubjectSeedData> loadClassSubjectSeedDataFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(
                new ClassPathResource("class-subjects.json").getInputStream(),
                new TypeReference<List<ClassSubjectSeedData>>() {
                }
        );
    }

    @Override
    public void run(String... args) throws Exception {
        List<ClassSubjectSeedData> classSubjectSeedData = loadClassSubjectSeedDataFromJson();
        for (ClassSubjectSeedData seedData : classSubjectSeedData) {
            Optional<SchoolClass> schoolClass = schoolClassRepository.findById(seedData.getClassId());
            if (schoolClass.isEmpty()) {
                continue;
            }
            SchoolClass schoolClassData = schoolClass.get();
            for (Long subjectId : seedData.getSubjectIds()) {
                Optional<Subject> subject = subjectRepository.findById(subjectId);
                if (subject.isEmpty()) {
                    System.out.println("Invalid subject id: " + subjectId);
                    continue;
                }
                Optional<ClassSubject> existingClassSubject = classSubjectRepository.findBySchoolClassIdAndSubjectId(seedData.getClassId(), subjectId);
                if (existingClassSubject.isPresent()) {
                    System.out.println("Subject " + existingClassSubject.get().getSubjectCode() + " already added");
                    continue;
                }
                ClassSubject classSubject = new ClassSubject();
                classSubject.setSubjectCode(subject.get().getSubjectCode() + seedData.getSubjectCodeSuffix());
                classSubject.setSchoolClass(schoolClassData);
                classSubject.setSubject(subject.get());
                classSubjectRepository.save(classSubject);
                System.out.println("Class Subject " + classSubject.getSubjectCode() + " has been added!!!");
            }
        }
    }
}
