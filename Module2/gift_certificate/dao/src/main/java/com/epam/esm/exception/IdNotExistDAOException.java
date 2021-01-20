package com.epam.esm.exception;

public class IdNotExistDAOException extends Exception {
    public IdNotExistDAOException() {
    }

    public IdNotExistDAOException(String message) {
        super(message);
    }

    public IdNotExistDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
