package com.newsHub.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PassWordValidator.class)
public @interface PassWordConstraint {
    String message() default "Invalid password";
    int min();
    int max();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
