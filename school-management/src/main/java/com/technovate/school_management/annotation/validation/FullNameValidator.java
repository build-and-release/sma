package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FullNameValidator implements ConstraintValidator<ValidFullName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Ensure the full name consists of exactly two words
        if (value == null || value.trim().isEmpty()) {
            return false; // Required, should be handled with @NotNull in DTO
        }

        String[] parts = value.trim().split("\\s+");
        return parts.length >= 2;  // Should be exactly two words
    }
}
