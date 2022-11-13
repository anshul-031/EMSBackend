package com.safehiring.ems.exceptio;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAlreadyExistsException extends RuntimeException {
    private String message;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
