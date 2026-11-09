package com.newsHub.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassWordValidator implements ConstraintValidator<PassWordConstraint,String> {

    private int min;
    private int max;

    @Override
    public void initialize(PassWordConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String passWord, ConstraintValidatorContext constraintValidatorContext) {
        if(passWord == null){
            return false;
        }
        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])"
                        + "(?=.*[^a-zA-Z0-9\\s])\\S{"
                        + min + "," + max + "}$";

        return passWord.matches(regex);
    }
}
