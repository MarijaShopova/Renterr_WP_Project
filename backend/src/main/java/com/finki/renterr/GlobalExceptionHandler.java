package com.finki.renterr;

import com.finki.renterr.api.exception.ImageFileStorageException;
import com.finki.renterr.api.exception.ResourceForbiddenException;
import com.finki.renterr.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resourseNotFoundHandler(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String resourceForbiddenHandler(ResourceForbiddenException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badCredentialsExceptionHandler(BadCredentialsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ImageFileStorageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String imageFileStorageExceptionHandler(ImageFileStorageException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, Set<String>> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        Map<String, Set<String>> errors = new HashMap<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.computeIfAbsent(field, key -> new HashSet<>()).add(message);
        }
        return errors;
    }
}
