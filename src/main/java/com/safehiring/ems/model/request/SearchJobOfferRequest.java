package com.safehiring.ems.model.request;

import lombok.Data;

@Data
public class SearchJobOfferRequest {
	private Long offerId;
	private String ern;
	private String tin;
	private String employeeCountry; // TO DO Add Field as Enum for country.
}
