package com.safehiring.ems.service;

import com.razorpay.RazorpayException;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse placeOrder(PaymentRequest paymentRequest) throws RazorpayException;
}


