package com.safehiring.ems.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderVo {

    private Double amount;

    @JsonProperty("amount_paid")
    private Double amountPaid;

    @JsonProperty("created_at")
    private Long createdAt;


    @JsonProperty("amount_due")
    private Double amountDue;

    private String currency;
    private String receipt;
    private String id;
    private String entity;

    @JsonProperty("offer_id")
    private String offerId;
    private String status;
    private int attempts;

    private String[] notes;
}
