package com.safehiring.ems.jpa.data;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.Hibernate;

import com.safehiring.ems.model.EmploymentOfferStatus;
import com.safehiring.ems.model.EmploymentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EMPLOYMENT_OFFER")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_Id", nullable = false)
    private Long offerId;
    private String ern;
    @Column(name="employee_country")
    private String employeeCountry;
    private String tin;
    @Column(name="employment_type")
    private EmploymentType employmentType;
    @Column(name="employer_org_name")
    private String employerOrgName;
    @Email
    @Column(name="employer_email")
    private String employerEmail;

    @Column(name="offer_updated_on")
    private LocalDate offerUpdatedOn;
    @Column(name="joining_date")
    private LocalDate joiningDate;
    @Column(name="enable_employment_offer_monitoring")
    private Boolean enableEmploymentOfferMonitoring;
    @Email
    @Column(name="updated_by")
    private String updatedBy;
    @Column(name="employment_offer_status")
    private EmploymentOfferStatus employmentOfferStatus;


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        final JobOffer jobOffer = (JobOffer) o;
        return this.offerId != null && Objects.equals(this.offerId, jobOffer.offerId);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
