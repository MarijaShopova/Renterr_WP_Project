package com.finki.renterr.api.exception;

public class ResourceForbiddenException extends RuntimeException{

    public ResourceForbiddenException(String message) {
        super(message);
    }
}
