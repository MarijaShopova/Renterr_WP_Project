package com.finki.renterr.api.validation;

import com.finki.renterr.service.AccountService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final AccountService accountService;

    public UniqueUsernameValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.accountService.checkUsernameAvailable(value);
    }
}
