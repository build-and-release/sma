package com.technovate.school_management.annotation.validation;

import com.technovate.school_management.dto.CreateGuardianDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AlternatePhoneNumberValidator implements ConstraintValidator<ValidAlternatePhoneNumber, CreateGuardianDto> {

    @Override
    public boolean isValid(CreateGuardianDto dto, ConstraintValidatorContext context) {
        String phoneNumber = dto.getPhoneNumber();
        String alternatePhoneNumber = dto.getAlternatePhoneNumber();

        // Alternate phone number is optional, so it's valid if it's null or empty
        if (alternatePhoneNumber == null || alternatePhoneNumber.trim().isEmpty()) {
            return true;
        }

        // Validate the alternate phone number (same rules as phoneNumber)
        boolean isValidAlternate = alternatePhoneNumber.matches("\\d{10,}") || alternatePhoneNumber.matches("\\+\\d{11,}");

        // Ensure that the alternate phone number is not the same as the primary phone number
        return isValidAlternate && !alternatePhoneNumber.equals(phoneNumber);
    }
}