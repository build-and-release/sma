package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null) {
            return false; // Null check is handled separately, usually with @NotNull
        }
        // Check if the gender is "male" or "female" (case-insensitive)
        return gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female");
    }
}
