package com.safehiring.ems.model.request;

import com.safehiring.ems.model.EmploymentType;
import lombok.Data;

@Data
public class JobOfferRequest {
    private Long id;
    private String ern;
    private String tin;
    private String joiningDate;
    private EmploymentType employmentType;
    private String employerOrgName;
    private String employerEmail;
    private Boolean enableEmploymentOfferMonitoring;
}
