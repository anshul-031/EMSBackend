package com.safehiring.ems.exception;

public class InvalidUserException extends RuntimeException {
    private final String message;

    public InvalidUserException(final String message) {
        super(message);
        this.message = message;
    }
}