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
    @Column(name = "offerId", nullable = false)
    private Long offerId;
    private String ern;
    private String employeeCountry;
    private String tin;
    private EmploymentType employmentType;
    private String employerOrgName;
    @Email
    private String employerEmail;
    private LocalDate offerUpdatedOn;
    private LocalDate joiningDate;
    private Boolean enableEmploymentOfferMonitoring;
    @Email
    private String updatedBy;
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
