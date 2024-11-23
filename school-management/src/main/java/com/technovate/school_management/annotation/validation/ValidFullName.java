package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FullNameValidator.class)
public @interface ValidFullName {
    String message() default "Full name must be at least two words (e.g., First Last)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}