package com.epam.esm.service.exception;

public class DuplicateEntryServiceException extends Exception {

    public DuplicateEntryServiceException() {
    }

    public DuplicateEntryServiceException(String message) {
        super(message);
    }

    public DuplicateEntryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
