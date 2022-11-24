package com.safehiring.ems.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAlreadyExistsException extends RuntimeException {
    private final String message;

    public UserAlreadyExistsException(final String message) {
        super(message);
        this.message = message;
    }
}
