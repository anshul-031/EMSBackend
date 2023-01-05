package com.safehiring.ems.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safehiring.ems.api.EmployerAPI;
import com.safehiring.ems.exception.InvalidJobOfferException;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;
import com.safehiring.ems.service.JobOfferService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/employer")
@Data
@Slf4j
public class EmployerController implements EmployerAPI {

	private final JobOfferService jobOfferService;

	@Override
	public ResponseEntity<JobOfferResponse> createJobOffers(@Valid final JobOfferRequest jobOfferRequest) {
		return new ResponseEntity<>(this.jobOfferService.saveJobOffer(jobOfferRequest), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<JobOfferResponse>> getAllEmploymentOffers(final String tin, final String employeecountry) {
		if (!employeecountry.equalsIgnoreCase("India")) {
			throw new IllegalArgumentException("Country not Supported as of now");
		}
		try {
			return new ResponseEntity<>(this.jobOfferService.getAllJobsByTin(tin), HttpStatus.OK);
		} catch (final InvalidJobOfferException e) {
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<JobOfferResponse> updateEmploymentOffers(@Valid final JobOfferRequest jobOfferRequest) {
		//TO DO
		//1. An Employer can update only his own offer letters.
		//2. Fetch the Employer Details like employerEmail.
		//2. Search the respective employers offers by employermail & tim number
		//3. Update the respective fields.

		JobOfferResponse jobOfferResponse = this.jobOfferService.updateJobOffer(jobOfferRequest);
		return new ResponseEntity<>(jobOfferResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<JobOfferResponse>> getEmploymentOffersByTin(String tin) {
		return new ResponseEntity<>(this.jobOfferService.getAllJobsByTin(tin), HttpStatus.OK);
	}

}
