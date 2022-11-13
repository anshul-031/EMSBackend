package com.safehiring.ems.service;

import com.safehiring.ems.controller.data.reqest.JobOfferRequest;
import com.safehiring.ems.controller.data.response.JobOfferResponse;
import com.safehiring.ems.exceptio.InvalidJobOfferException;
import java.util.List;


public interface JobOfferService {
    JobOfferResponse getJobOfferById(Long id) throws InvalidJobOfferException;

    List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException;

    JobOfferResponse saveJobOffer(JobOfferRequest jobOfferRequest);

    JobOfferResponse updateJobOffer(JobOfferRequest jobOfferRequest);

    void deleteJobOffer(Long jobId);

    List<JobOfferResponse> getAllJobsByPanNumber(String panNumber) throws InvalidJobOfferException;
}
