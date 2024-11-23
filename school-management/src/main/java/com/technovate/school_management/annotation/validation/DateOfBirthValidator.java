package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class DateOfBirthValidator implements ConstraintValidator<ValidDateOfBirth, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false; // Handle @NotNull separately
        }
        // Ensure dateOfBirth is in the past and the student is at least 2 years old
        return dateOfBirth.isBefore(LocalDate.now()) && Period.between(dateOfBirth, LocalDate.now()).getYears() >= 2;
    }
}
