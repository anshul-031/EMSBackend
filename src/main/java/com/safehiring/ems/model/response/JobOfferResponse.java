package com.safehiring.ems.model.response;

import com.safehiring.ems.model.EmploymentOfferStatus;
import com.safehiring.ems.model.EmploymentType;
import lombok.Data;

@Data
public class JobOfferResponse {
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
    private EmploymentOfferStatus employmentOfferStatus;
}
