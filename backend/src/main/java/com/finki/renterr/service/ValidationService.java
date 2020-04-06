package com.finki.renterr.service;

import com.finki.renterr.api.request.AccountEditRequest;
import com.finki.renterr.model.domain.Account;
import com.finki.renterr.model.domain.Apartment;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service
public class ValidationService {

    private final Validator validator;

    public ValidationService(Validator validator) {
        this.validator = validator;
    }

    public void validateAccount(Account account) {
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateAccountEditRequest(AccountEditRequest request) {
        Set<ConstraintViolation<AccountEditRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    public void validateApartment(Apartment apartment) {
        Set<ConstraintViolation<Apartment>> violations = validator.validate(apartment);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
