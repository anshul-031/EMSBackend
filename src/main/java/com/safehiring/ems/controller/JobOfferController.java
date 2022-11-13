package com.safehiring.ems.controller;


import com.safehiring.ems.controller.data.reqest.JobOfferRequest;
import com.safehiring.ems.controller.data.response.JobOfferResponse;
import com.safehiring.ems.exceptio.InvalidJobOfferException;
import com.safehiring.ems.service.JobOfferService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/api/offer")
@CrossOrigin("*")
@Data
@AllArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @GetMapping("/view/all")
    public List<JobOfferResponse> getAllJobOffers() throws InvalidJobOfferException {

        return jobOfferService.getAllJobOffers();
    }

    @GetMapping("/view/job/{panNumber}")
    public List<JobOfferResponse> getAllJobOffers(@PathVariable String panNumber) throws InvalidJobOfferException {

        return jobOfferService.getAllJobsByPanNumber(panNumber);
    }

    @PostMapping("/opt/job")
    public JobOfferResponse createJobOffers(@RequestBody @Valid JobOfferRequest jobOfferRequest) {

        return jobOfferService.saveJobOffer(jobOfferRequest);

    }

    @PutMapping("/opt/job")
    public JobOfferResponse updateJobOffers(@RequestBody @Valid JobOfferRequest jobOfferRequest) {

        return jobOfferService.updateJobOffer(jobOfferRequest);

    }

    @DeleteMapping("/opt/job/{jobId}")
    public String deleteJobOffers(@PathVariable Long jobId) {
        jobOfferService.deleteJobOffer(jobId);
        return "Job deleted successfully";
    }
}
