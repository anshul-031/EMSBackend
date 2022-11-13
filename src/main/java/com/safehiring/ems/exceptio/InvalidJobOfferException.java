package com.safehiring.ems.exceptio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidJobOfferException extends EmsException {
    private final String code;
    private final String message;

}
