package com.safehiring.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmsException extends RuntimeException {
    public static final String INVALID_JOB_OFFER = "INVALID_JOB_OFFER";
    private String code;
    private String message;

}
