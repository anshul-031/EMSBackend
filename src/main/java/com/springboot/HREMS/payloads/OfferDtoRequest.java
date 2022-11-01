package com.springboot.HREMS.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OfferDtoRequest {
    private String id;
    private String name;
    private String company;
    private String offerReceivedDate;
    private String joiningDate;
    private String jobTitle;
    private String CTC;
    private String panNumber;
}
