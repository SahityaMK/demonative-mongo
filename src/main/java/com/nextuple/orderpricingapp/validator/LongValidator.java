package com.nextuple.orderpricingapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongValidator implements ConstraintValidator<ValidLongNumber, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return value.longValue() != 0;
    }
}
