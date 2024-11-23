package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GuardianTypeValidator.class) // Link to the validator class
@Target({ ElementType.FIELD, ElementType.PARAMETER }) // Can be applied to fields and parameters
@Retention(RetentionPolicy.RUNTIME) // Retain at runtime
public @interface ValidGuardianType {
    String message() default "Guardian type must be either 'parent' or 'legal_guardian'"; // Default error message

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
