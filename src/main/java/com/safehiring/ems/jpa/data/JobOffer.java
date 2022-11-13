package com.safehiring.ems.jpa.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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
    private String name;
    private String company;
    private String offerReceivedDate;
    private String joiningDate;
    private String jobTitle;
    private String ctc;
    private String panNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobOffer jobOffer = (JobOffer) o;
        return id != null && Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
