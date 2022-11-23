package com.safehiring.ems.controller.data.reqest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployerRegistrationRequest {

    @NotEmpty(message = "{employer.registration.email}")
    @Email(message = "{employer.registration.email}")
    private String email;

    @NotEmpty(message = "{employer.registration.password}")
    private String password;

    @NotEmpty(message = "{employer.registration.orgName}")
    private String orgName;

    @NotEmpty(message = "{employer.registration.country}")
    private String country;

    @NotEmpty(message = "{employer.registration.website}")
    private String website;

    @NotNull(message = "{employer.registration.mobile}")
    private Long mobile;

    private String referredBy;

    private String group;
}
