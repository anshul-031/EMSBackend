package com.safehiring.ems.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.safehiring.ems.exception.InvalidJobOfferException;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;
import com.safehiring.ems.service.JobOfferService;
import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * TO DO Least Priority.
 * I (Anshul) don't think we need this Controller. There would be mostly two types of API's, Employer or Employee. so
 * two controller Employee & Employer.
 * Hence Would be deleting this controller in Future. Please confirm before deleting this.
 */

//@RestController
//@RequestMapping("/v1/api/offer")
@CrossOrigin("*")
@Data
@AllArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    //    @GetMapping("/view/all")
    public List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException {

        return this.jobOfferService.getAllJobOffers();
    }

    //    @GetMapping("/view/job/{tin}")
    public List<JobOfferResponse> getAllJobOffers(@PathVariable final String tin) throws InvalidJobOfferException {

        return this.jobOfferService.getAllJobsByTin(tin);
    }

    //    @PostMapping("/opt/job")
    public JobOfferResponse createJobOffers(@RequestBody @Valid final JobOfferRequest jobOfferRequest) {
        return this.jobOfferService.saveJobOffer(jobOfferRequest);

    }

    //    @PutMapping("/opt/job")
    public JobOfferResponse updateJobOffers(@RequestBody @Valid final JobOfferRequest jobOfferRequest) {

        return this.jobOfferService.updateJobOffer(jobOfferRequest);

    }

    //    @DeleteMapping("/opt/job/{jobId}")
    public String deleteJobOffers(@PathVariable final Long jobId) {
        this.jobOfferService.deleteJobOffer(jobId);
        return "Job deleted successfully";
    }
}
