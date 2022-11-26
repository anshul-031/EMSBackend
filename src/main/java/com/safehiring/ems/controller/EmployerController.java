package com.safehiring.ems.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.safehiring.ems.api.EmployerAPI;
import com.safehiring.ems.exception.InvalidJobOfferException;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;
import com.safehiring.ems.service.JobOfferService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
//TO DO
// Protect This controller with Scope= Employer meaning only an employer can access this API
//@RequiresScope({ Scope.Employer })
@RestController
public class EmployerController implements EmployerAPI {

	private final JobOfferService jobOfferService;

	@Override
	public ResponseEntity<JobOfferResponse> createJobOffers(@Valid final JobOfferRequest jobOfferRequest) {
		return new ResponseEntity<>(this.jobOfferService.saveJobOffer(jobOfferRequest), HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<JobOfferResponse>> getAllEmploymentOffers(final String tin) {
		try {
			return new ResponseEntity<>(this.jobOfferService.getAllJobsByTin(tin), HttpStatus.OK);
		} catch (final InvalidJobOfferException e) {
			return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
		}
	}

	@Override
	public ResponseEntity<JobOfferResponse> updateEmploymentOffers(@Valid final JobOfferRequest jobOfferRequest) {
		//TO DO
		//1. An Employer can update only his own offer letters.
		//2. Fetch the Employer Details like employerEmail.
		//2. Search the respective employers offers by employermail & tim number
		//3. Update the respective fields.
		return null;
	}
}
