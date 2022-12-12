package com.safehiring.ems.model.request;

import lombok.Data;

@Data
public class OrderVo {

    private Double amount;
    private Double amountPaid;
    private Long createdAt;
    private Double amountDue;
    private String currency;
    private String receipt;
    private String id;
    private String entity;
    private String offerId;
    private String status;
    private int attempts;

}
