package com.safehiring.ems.jpa.repository;

import java.util.List;

import com.safehiring.ems.model.response.JobOfferResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safehiring.ems.jpa.data.JobOffer;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByTin(String tin);

    List<JobOffer> findByTinAndEmployerEmail(String tin, String employerEmail);
}
