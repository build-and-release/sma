package com.technovate.school_management.seedDB;

import com.technovate.school_management.entity.Subject;
import com.technovate.school_management.repository.SubjectRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubjectSeeder implements CommandLineRunner {
    private final SubjectRepository subjectRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        // Load JSON file
        List<Subject> subjects = loadSubjectsFromJson();

        // Check if subjects exist in the database, and if not, save them
        for (Subject subject : subjects) {
            Optional<Subject> existingSubject = subjectRepository.findByTitleAndLevel(subject.getTitle(), subject.getLevel());
            if (existingSubject.isEmpty()) {
                subjectRepository.save(subject);
                System.out.println("Saved subject: " + subject.getTitle());
            } else {
                System.out.println("Subject already exists: " + subject.getTitle());
            }
        }
    }

    private List<Subject> loadSubjectsFromJson() throws IOException {
        // Load JSON from resources
        return objectMapper.readValue(
                new ClassPathResource("subjects.json").getInputStream(),
                new TypeReference<List<Subject>>() {}
        );
    }
}
