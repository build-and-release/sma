package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class) // Link to the validator
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // Applicable to fields and method parameters
@Retention(RetentionPolicy.RUNTIME) // Retain at runtime
public @interface ValidGender {
    String message() default "Gender must be either 'male' or 'female'"; // Default error message

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
