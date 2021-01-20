package com.epam.esm.service.exception;

public class IdNotExistServiceException extends Exception {

    public IdNotExistServiceException() {
    }

    public IdNotExistServiceException(String message) {
        super(message);
    }

    public IdNotExistServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNotExistServiceException(Throwable cause) {
        super(cause);
    }
}
