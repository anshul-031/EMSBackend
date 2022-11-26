package com.safehiring.ems.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;

@CrossOrigin("*")
@RequestMapping("/v1/employer")
public interface EmployerAPI {

	@PostMapping(value = "/employmentoffer", produces = { "application/json" }, consumes = { "application/json" })
	ResponseEntity<JobOfferResponse> createJobOffers(@RequestBody @Valid final JobOfferRequest jobOfferRequest);

	@GetMapping(value = "/employmentoffer/{tin}")
	ResponseEntity<List<JobOfferResponse>> getAllEmploymentOffers(@PathVariable final String tin);

	@PutMapping(value = "/employmentoffer", produces = { "application/json" }, consumes = { "application/json" })
	ResponseEntity<JobOfferResponse> updateEmploymentOffers(@RequestBody @Valid final JobOfferRequest jobOfferRequest);

}
