package com.safehiring.ems.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.safehiring.ems.api.EmployerAPI;
import com.safehiring.ems.model.request.JobOfferRequest;
import com.safehiring.ems.model.response.JobOfferResponse;
import com.safehiring.ems.service.JobOfferService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin("*")
@Data
@Slf4j
//TO DO Protect This controller with Scope= Employer meaning only an employer can access this API
//@RequiresScope({ Scope.Employer })
public class EmployerController implements EmployerAPI {

	private final JobOfferService jobOfferService;

	@Override
	public ResponseEntity<JobOfferResponse> createJobOffers(@Valid final JobOfferRequest jobOfferRequest) {
		return new ResponseEntity<>(this.jobOfferService.saveJobOffer(jobOfferRequest), HttpStatus.CREATED);
	}
	
}
