package com.safehiring.ems.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.safehiring.ems.model.EmploymentType;
import lombok.Data;

@Data
public class JobOfferRequest {
    private Long offerId;
    private String ern;
    private String tin;
    private String employeeCountry;
    @NotNull(message = "{employer.jobOffer.joiningDate}")
    @Future(message = "{employer.jobOffer.joiningDate}")
    private LocalDate joiningDate; //TO DO Check for Future dates only
    private EmploymentType employmentType;
    private String employerOrgName;
    private String employerEmail;
    private Boolean enableEmploymentOfferMonitoring;
}
