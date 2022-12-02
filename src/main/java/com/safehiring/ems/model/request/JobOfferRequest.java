package com.safehiring.ems.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.safehiring.ems.model.EmploymentType;
import lombok.Data;

@Data
public class JobOfferRequest {
    private Long offerId;
    private String ern;
    private String tin;
    private String employeeCountry; // TO DO Use ENUM for country
    @NotNull(message = "{employer.jobOffer.joiningDate}")
    @Future(message = "{employer.jobOffer.joiningDate}")
    private LocalDate joiningDate;
    private EmploymentType employmentType;
    private String employerOrgName;
    @Email
    private String employerEmail;
    private Boolean enableEmploymentOfferMonitoring;
}
