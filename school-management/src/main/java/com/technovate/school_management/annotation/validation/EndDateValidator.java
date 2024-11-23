package com.technovate.school_management.annotation.validation;

import com.technovate.school_management.dto.CreateTermDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EndDateValidator implements ConstraintValidator<ValidEndDate, CreateTermDto> {

    @Override
    public boolean isValid(CreateTermDto dto, ConstraintValidatorContext context) {
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        // Null check for endDate
        if (endDate == null) {
            return true; // Let @NotNull handle null checks
        }

        // Validate that endDate is after today and after startDate (if startDate is not null)
        if (endDate.isBefore(LocalDate.now()) || (startDate != null && endDate.isBefore(startDate))) {
            // Add custom message for better error feedback
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be after today and after the start date")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}