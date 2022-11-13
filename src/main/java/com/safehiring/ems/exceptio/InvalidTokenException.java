package com.safehiring.ems.exceptio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidTokenException extends EmsException {
    private final String message;

}