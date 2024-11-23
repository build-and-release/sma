package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})  // Validation at the class level
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AlternatePhoneNumberValidator.class)
public @interface ValidAlternatePhoneNumber {
    String message() default "Alternate phone number must be valid and not the same as the primary phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
