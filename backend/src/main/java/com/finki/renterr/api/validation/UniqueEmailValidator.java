package com.finki.renterr.api.validation;

import com.finki.renterr.service.AccountService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AccountService accountService;

    public UniqueEmailValidator(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.accountService.checkEmailAvailable(value);
    }
}
