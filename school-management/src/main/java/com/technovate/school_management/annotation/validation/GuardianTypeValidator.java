package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GuardianTypeValidator implements ConstraintValidator<ValidGuardianType, String> {

    @Override
    public boolean isValid(String guardianType, ConstraintValidatorContext context) {
        if (guardianType == null) {
            return false; // Null check is handled separately, usually with @NotNull
        }
        // Check if the guardian type is "parent" or "legal_guardian" (case-insensitive)
        return guardianType.equalsIgnoreCase("parent") || guardianType.equalsIgnoreCase("legal_guardian");
    }
}
