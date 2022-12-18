package com.safehiring.ems.model.request;

import lombok.Data;
 
@Data
public class OrderResponse {
    private String applicationFee;
    private String razorpayOrderId;
    private String secretKey;
}