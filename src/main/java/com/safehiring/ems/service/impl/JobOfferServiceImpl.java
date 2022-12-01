package com.safehiring.ems.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.safehiring.ems.exception.EmsException;
import com.safehiring.ems.exception.InvalidJobOfferException;
import com.safehiring.ems.jpa.data.JobOffer;
import com.safehiring.ems.jpa.repository.JobOfferRepository;
import com.safehiring.ems.model.EmploymentOfferStatus;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;
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
        /***
         * TO DO
         * Check if there is already an "ACTIVE" Employment Offer by the current Employer to the candidate, throw an
         * exception, "Employment Offer already exist by the {{CurrentEmployer}}, please use the update API to update
         * the employment offer"
         */
        final JobOffer jobOffer = new JobOffer();
        BeanUtils.copyProperties(jobOfferRequest, jobOffer);
        jobOffer.setId(null);
        jobOffer.setEmploymentOfferStatus(EmploymentOfferStatus.ACTIVE);
        jobOffer.setUpdatedBy("employer@gmail.com"); //TO DO fetch Username from JWT token
        jobOffer.setOfferUpdatedOn(LocalDate.now());
        this.jobOfferRepository.save(jobOffer);
        /***
         * TO DO
         * Check if the new Employee hired already has another 'ACTIVE' Employment offer, send other previous employer
         * who have 'ACTIVE' employment offer, an email intimation that the candidate received a new offer
         */
        return this.populateJobOfferResponse(jobOffer);
    }

    @Override
    public JobOfferResponse updateJobOffer(final JobOfferRequest jobOfferRequest) {
        final JobOffer jobOffer =
                this.jobOfferRepository.findById(jobOfferRequest.getOfferId()).orElseThrow(() -> new InvalidJobOfferException(EmsException.INVALID_JOB_OFFER, "Invalid job"));
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
