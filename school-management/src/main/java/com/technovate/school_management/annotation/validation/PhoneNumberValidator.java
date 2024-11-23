package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return false; // Handle @NotNull separately
        }

        // Valid if it's a 10+ digit number or a number that starts with + and has 11+ digits
        return value.matches("\\d{10,}") || value.matches("\\+\\d{11,}");
    }
}
