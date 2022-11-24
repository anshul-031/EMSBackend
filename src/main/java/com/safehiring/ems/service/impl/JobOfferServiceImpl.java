package com.safehiring.ems.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.safehiring.ems.controller.data.reqest.EmploymentOfferStatus;
import com.safehiring.ems.controller.data.reqest.JobOfferRequest;
import com.safehiring.ems.controller.data.response.JobOfferResponse;
import com.safehiring.ems.exceptio.EmsException;
import com.safehiring.ems.exceptio.InvalidJobOfferException;
import com.safehiring.ems.jpa.data.JobOffer;
import com.safehiring.ems.jpa.repository.JobOfferRepository;
import com.safehiring.ems.service.JobOfferService;
import lombok.Data;

@Service
@Data
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository jobOfferRepository;

    @Override
    public JobOfferResponse getJobOfferById(final Long id) throws InvalidJobOfferException {
        return this.populateJobOfferResponse(this.jobOfferRepository.findById(id).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "JobOffer doesn't exists")));
    }

    @Override
    public List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException {
        return this.populateJobOfferResponses(this.jobOfferRepository.findAll());
    }

    @Override
    public JobOfferResponse saveJobOffer(final JobOfferRequest jobOfferRequest) {
        final JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferRequest, jobOffer);
        jobOffer.setId(null);
        jobOffer.setEmploymentOfferStatus(EmploymentOfferStatus.ACTIVE);
        this.jobOfferRepository.save(jobOffer);
        return this.populateJobOfferResponse(jobOffer);
    }

    @Override
    public JobOfferResponse updateJobOffer(final JobOfferRequest jobOfferRequest) {
        final JobOffer jobOffer = this.jobOfferRepository.findById(jobOfferRequest.getId()).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "Invalid job"));
        BeanUtils.copyProperties(jobOfferRequest, jobOffer);
        this.jobOfferRepository.save(jobOffer);
        return this.populateJobOfferResponse(jobOffer);
    }

    @Override
    public void deleteJobOffer(final Long jobId) {
        this.jobOfferRepository.findById(jobId).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "Invalid JobId"));
        this.jobOfferRepository.deleteById(jobId);
    }

    @Override
    public List<JobOfferResponse> getAllJobsByTin(final String tin) throws InvalidJobOfferException {
        return this.populateJobOfferResponses(this.jobOfferRepository.findByTin(tin));

    }

    private List<JobOfferResponse> populateJobOfferResponses(final List<JobOffer> jobOffers) throws InvalidJobOfferException {
        final List<JobOfferResponse> jobOfferResponses = new ArrayList<>(jobOffers.size());
        if (jobOffers.isEmpty()) {
            throw new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "No Jobs found");
        }
        jobOffers.forEach(jobOffer -> jobOfferResponses.add(this.populateJobOfferResponse(jobOffer)));
        return jobOfferResponses;
    }

    private JobOfferResponse populateJobOfferResponse(final JobOffer jobOffer) {
        final JobOfferResponse jobOfferResponse = new JobOfferResponse();
        BeanUtils.copyProperties(jobOffer, jobOfferResponse);
        return jobOfferResponse;
    }
}
