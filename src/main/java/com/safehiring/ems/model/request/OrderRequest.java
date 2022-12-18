package com.safehiring.ems.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OrderRequest {
    @NotEmpty(message = "{employer.registration.custName}")
    private String customerName;

    @NotEmpty(message = "{employer.registration.email}")
    @Email(message = "{employer.registration.email}")
    private String email;

    @NotEmpty(message = "{employer.registration.mobile}")
    private String phoneNumber;
    @NotNull(message = "{employer.registration.amount}")
    private String amount;
}