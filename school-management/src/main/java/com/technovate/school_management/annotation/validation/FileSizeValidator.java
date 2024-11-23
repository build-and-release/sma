package com.technovate.school_management.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<ValidFileSize, MultipartFile> {

    private double maxFileSizeInMB;

    @Override
    public void initialize(ValidFileSize constraintAnnotation) {
        this.maxFileSizeInMB = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return false; // Handle @NotNull separately
        }

        // Convert MB to bytes for comparison
        double maxFileSizeInBytes = maxFileSizeInMB * 1024 * 1024;

        // Validate file size does not exceed the specified max size in MB
        return file.getSize() <= maxFileSizeInBytes;
    }
}
