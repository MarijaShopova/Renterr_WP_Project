package com.finki.renterr.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LocationCheckValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LocationCheck {

    public String message() default "City and municipality combo is invalid!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}
