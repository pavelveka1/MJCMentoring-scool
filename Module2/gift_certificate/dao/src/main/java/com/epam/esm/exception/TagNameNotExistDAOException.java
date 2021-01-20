package com.epam.esm.exception;

public class TagNameNotExistDAOException extends Exception {

    public TagNameNotExistDAOException() {
    }

    public TagNameNotExistDAOException(String message) {
        super(message);
    }

    public TagNameNotExistDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
