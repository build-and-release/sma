package com.technovate.school_management.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TermNameValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTermName {
    String message() default "Invalid term name. It must be 'first', 'second', or 'third'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
