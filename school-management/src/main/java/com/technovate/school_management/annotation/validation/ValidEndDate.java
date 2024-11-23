package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // Apply to classes
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EndDateValidator.class)
public @interface ValidEndDate {
    String message() default "End date must be after the start date and today";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
