package com.technovate.school_management.dto;

import com.technovate.school_management.annotation.validation.ValidFullName;
import com.technovate.school_management.annotation.validation.ValidPhoneNumber;
import com.technovate.school_management.annotation.validation.ValidAlternatePhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidAlternatePhoneNumber  // Class-level validation for alternate phone number
public class CreateGuardianDto {

    @NotNull(message = "Full name is required")
    @ValidFullName  // Custom validation for full name
    private String fullName;

    @NotNull(message = "Address is required")
    private String address;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @ValidPhoneNumber  // Custom validation for phone number
    private String phoneNumber;

//    @ValidPhoneNumber  // Custom validation for alternate phone number (if supplied)
    private String alternatePhoneNumber;

    @NotNull(message = "Nationality is required")
    private String nationality;
}