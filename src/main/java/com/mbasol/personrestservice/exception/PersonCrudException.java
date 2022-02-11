package com.mbasol.personrestservice.exception;

public class PersonCrudException extends RuntimeException {

    public PersonCrudException() {
    }

    public PersonCrudException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PersonCrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonCrudException(String message) {
        super(message);
    }

    public PersonCrudException(Throwable cause) {
        super(cause);
    }
}