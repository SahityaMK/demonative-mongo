package com.nextuple.orderpricingapp.validator;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@javax.validation.Constraint(validatedBy = {OffsetDateTimeValidator.class})
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOffsetDateTime{

    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}


