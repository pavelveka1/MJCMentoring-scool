package com.epam.esm.service.exception;

public class RequestParamServiceException extends Exception {

    public RequestParamServiceException() {
    }

    public RequestParamServiceException(String message) {
        super(message);
    }

    public RequestParamServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
