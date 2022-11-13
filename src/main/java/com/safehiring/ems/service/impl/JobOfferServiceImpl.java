package com.safehiring.ems.service.impl;

import com.safehiring.ems.controller.data.reqest.JobOfferRequest;
import com.safehiring.ems.controller.data.response.JobOfferResponse;
import com.safehiring.ems.exceptio.EmsException;
import com.safehiring.ems.exceptio.InvalidJobOfferException;
import com.safehiring.ems.jpa.data.JobOffer;
import com.safehiring.ems.jpa.repository.JobOfferRepository;
import com.safehiring.ems.service.JobOfferService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    @Override
    public JobOfferResponse getJobOfferById(Long id) throws InvalidJobOfferException {
        return populateJobOfferResponse(jobOfferRepository.findById(id).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "JobOffer doesn't exists")));
    }

    @Override
    public List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException {
        return populateJobOfferResponses(jobOfferRepository.findAll());
    }

    @Override
    public JobOfferResponse saveJobOffer(JobOfferRequest jobOfferRequest) {
        JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferRequest, jobOffer);
        jobOffer.setId(null);
        jobOfferRepository.save(jobOffer);
        return populateJobOfferResponse(jobOffer);
    }

    @Override
    public JobOfferResponse updateJobOffer(JobOfferRequest jobOfferRequest) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferRequest.getId()).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "Invalid job"));
        BeanUtils.copyProperties(jobOfferRequest, jobOffer);
        jobOfferRepository.save(jobOffer);
        return populateJobOfferResponse(jobOffer);
    }

    @Override
    public void deleteJobOffer(Long jobId) {
        jobOfferRepository.findById(jobId).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "Invalid JobId"));
        jobOfferRepository.deleteById(jobId);
    }

    @Override
    public List<JobOfferResponse> getAllJobsByPanNumber(String panNumber) throws InvalidJobOfferException {
        return populateJobOfferResponses(jobOfferRepository.findByPanNumber(panNumber));

    }

    private List<JobOfferResponse> populateJobOfferResponses(List<JobOffer> jobOffers) throws InvalidJobOfferException {
        List<JobOfferResponse> jobOfferResponses = new ArrayList<>(jobOffers.size());
        if (jobOffers.isEmpty()) {
            throw new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "No Jobs found");
        }
        jobOffers.forEach(jobOffer -> jobOfferResponses.add(populateJobOfferResponse(jobOffer)));
        return jobOfferResponses;
    }

    private JobOfferResponse populateJobOfferResponse(JobOffer jobOffer) {
        JobOfferResponse jobOfferResponse = new JobOfferResponse();
        BeanUtils.copyProperties(jobOffer, jobOfferResponse);
        return jobOfferResponse;
    }
}
