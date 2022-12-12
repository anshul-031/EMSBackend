package com.safehiring.ems.service.payment;

import com.razorpay.RazorpayException;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;

public interface PaymentGateway {

    PaymentResponse placeOrder(PaymentRequest paymentRequest) throws RazorpayException;

}
