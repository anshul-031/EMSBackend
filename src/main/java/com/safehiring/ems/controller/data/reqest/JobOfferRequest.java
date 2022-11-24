package com.safehiring.ems.controller.data.reqest;

import lombok.Data;

@Data
public class JobOfferRequest {
    private Long id;
    private String ern;
    private String tin;
    private String offerReceivedDate;
    private String joiningDate;
    private EmploymentType employmentType;
    private String employerOrgName;
    private String employerEmail;
    private String updatedBy;
    private Boolean enableEmploymentOfferMonitoring;
}
