package com.nextuple.orderpricingapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.OffsetDateTime;

public class OffsetDateTimeValidator implements ConstraintValidator<ValidOffsetDateTime, OffsetDateTime> {

    @Override
    public void initialize(ValidOffsetDateTime constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(OffsetDateTime value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        System.out.println("OffsetDateTimeValidator.isValid"+value.toString());
        return true;
    }
}
