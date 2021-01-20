package com.epam.esm.exception;

public class DuplicateEntryDAOException extends Exception {

    public DuplicateEntryDAOException(String message) {
        super(message);
    }

    public DuplicateEntryDAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
