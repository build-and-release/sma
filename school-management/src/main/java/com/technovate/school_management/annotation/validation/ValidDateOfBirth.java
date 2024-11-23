package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateOfBirth {
    String message() default "Date of birth must be before today and the student must be at least 2 years old";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
