package com.epam.esm.exception;

public class RequestParamDAOException extends Exception {

    public RequestParamDAOException() {
    }

    public RequestParamDAOException(String message) {
        super(message);
    }

    public RequestParamDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
