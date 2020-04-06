package com.finki.renterr.api.exception;

public class ImageFileStorageException extends RuntimeException {

    public ImageFileStorageException(String message) {
        super(message);
    }

    public ImageFileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

