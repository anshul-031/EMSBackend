package com.safehiring.ems.controller.data.response;

import lombok.Data;

@Data
public class JobOfferResponse {
    private Long id;
    private String name;
    private String company;
    private String offerReceivedDate;
    private String joiningDate;
    private String jobTitle;
    private String ctc;
    private String panNumber;
}
