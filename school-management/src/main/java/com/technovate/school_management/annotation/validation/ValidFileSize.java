package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFileSize {

    // Custom error message
    String message() default "File size must be less than {maxSize} MB";

    // Maximum file size in MB (default is 2 MB)
    double maxSize() default 2.0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
