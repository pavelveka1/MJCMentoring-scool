package com.epam.esm.service.exception;

public class TagNameNotExistServiceException extends Exception {

    public TagNameNotExistServiceException() {
    }

    public TagNameNotExistServiceException(String message) {
        super(message);
    }

    public TagNameNotExistServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
