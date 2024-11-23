package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TermNameValidator implements ConstraintValidator<ValidTermName, String> {

    private static final String[] VALID_NAMES = { "first", "second", "third" };

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull for null check
        }
        for (String validName : VALID_NAMES) {
            if (validName.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}