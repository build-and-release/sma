package com.technovate.school_management.dto;

import com.technovate.school_management.annotation.validation.ValidDateOfBirth;
import com.technovate.school_management.annotation.validation.ValidFileSize;
import com.technovate.school_management.annotation.validation.ValidGender;
import com.technovate.school_management.annotation.validation.ValidGuardianType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentDto {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    private String middleName;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Date of birth is required")
    @ValidDateOfBirth
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    @ValidGender // Custom annotation to validate gender
    private String gender;

    @NotNull(message = "Guardian email is required")
    @Email(message = "Email should be valid")
    private String guardianEmail;

    @NotNull(message = "Guardian type is required")
    @ValidGuardianType // Custom annotation to validate guardian type
    private String guardianType;

    @NotNull(message = "Nationality is required")
    private String nationality;

    @NotNull(message = "Passport image is required")
    @ValidFileSize(maxSize = 1.25) // Dynamic file size (e.g., max 1.25 MB)
    private MultipartFile passportImage;
}
