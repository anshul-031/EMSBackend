package com.safehiring.ems.api;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;

@RequestMapping("/v1/employer")
public interface EmployerAPI {

	@PostMapping(value = "/employmentoffer", produces = { "application/json" }, consumes = { "application/json" })
	ResponseEntity<JobOfferResponse> createJobOffers(@RequestBody @Valid final JobOfferRequest jobOfferRequest);


}
