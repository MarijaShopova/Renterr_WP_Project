package com.finki.renterr.api.validation;

import com.finki.renterr.model.domain.Account;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Account> {

    @Override
    public boolean isValid(Account value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String confirmPassword = value.getConfirmPassword();
        return password != null && password.equals(confirmPassword);
    }
}
