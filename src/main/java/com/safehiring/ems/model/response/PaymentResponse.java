package com.safehiring.ems.model.response;

import lombok.Data;

@Data
public class PaymentResponse {

    private String orderId;

    private Boolean success;
}
