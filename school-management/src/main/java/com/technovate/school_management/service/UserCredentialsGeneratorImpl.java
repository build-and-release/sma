package com.technovate.school_management.service;

import com.technovate.school_management.entity.User;
import com.technovate.school_management.repository.StudentRepository;
import com.technovate.school_management.repository.UserRepository;
import com.technovate.school_management.service.contracts.UsersCredentialsGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialsGeneratorImpl implements UsersCredentialsGenerator {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public String generateUsername(String firstName, String lastName) {
        String initialUsername = firstName.charAt(0) + lastName;
        Optional<User> existingUser = userRepository.findByUsername(initialUsername);
        if (existingUser.isEmpty()) {
            return initialUsername;
        }
        int initialPrefix = 1;
        boolean isUsernameAvailable = false;
        String newUsername = "";
        while(!isUsernameAvailable) {
            String prefixedUsername = firstName.charAt(0) + lastName + initialPrefix;
            Optional<User> userExists = userRepository.findByUsername(prefixedUsername);
            if (userExists.isEmpty()) {
                newUsername = prefixedUsername;
                isUsernameAvailable = true;
            }
            initialPrefix++;
        }
        return newUsername;
    }

    @Override
    public String generateIdNumber() {
        String idNumber = idGenerator();
        while(studentRepository.findByIdNumber(idNumber).isPresent()) {
            idNumber = idGenerator();
        }
        return idNumber;
    }

    private String idGenerator() {
        int year = LocalDate.now().getYear();

        // Generate the numeric part of the ID
        String numericPart = String.format("%06d", (int)(Math.random() * 1000000)); // Generates a 6-digit random number

        // Set the idNumber in the format 000000/{year}
        return numericPart + "/" + year;
    }
}
