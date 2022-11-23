package com.safehiring.ems.service;

import java.util.List;

import com.safehiring.ems.controller.data.reqest.JobOfferRequest;
import com.safehiring.ems.controller.data.response.JobOfferResponse;
import com.safehiring.ems.exceptio.InvalidJobOfferException;


public interface JobOfferService {
    JobOfferResponse getJobOfferById(Long id) throws InvalidJobOfferException;

    List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException;

    JobOfferResponse saveJobOffer(JobOfferRequest jobOfferRequest);

    JobOfferResponse updateJobOffer(JobOfferRequest jobOfferRequest);

    void deleteJobOffer(Long jobId);

    List<JobOfferResponse> getAllJobsByTin(String tin) throws InvalidJobOfferException;
}
