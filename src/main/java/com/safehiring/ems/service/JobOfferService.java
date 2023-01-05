package com.safehiring.ems.service;

import java.util.List;

import com.safehiring.ems.exception.InvalidJobOfferException;
import com.safehiring.ems.jpa.data.JobOffer;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;


public interface JobOfferService {
    JobOfferResponse getJobOfferById(Long id) throws InvalidJobOfferException;

    List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException;

    JobOfferResponse saveJobOffer(JobOfferRequest jobOfferRequest);

    JobOfferResponse updateJobOffer(JobOfferRequest jobOfferRequest);

    void deleteJobOffer(Long jobId);

    List<JobOfferResponse> getAllJobsByTin(String tin) throws InvalidJobOfferException;

    List<JobOffer> getAllJobsByTinAndEmployerEmail(String tin, String employerEmail) throws InvalidJobOfferException;

}
