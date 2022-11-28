package com.safehiring.ems.jpa.data;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Hibernate;

import com.safehiring.ems.model.EmploymentOfferStatus;
import com.safehiring.ems.model.EmploymentType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "JOB_OFFER")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String ern;
    private String tin;
    private EmploymentType employmentType;
    private String employerOrgName;
    private String employerEmail;
    private String offerUpdatedOn;
    private String joiningDate;
    private Boolean enableEmploymentOfferMonitoring;
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
        return this.id != null && Objects.equals(this.id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
