package com.safehiring.ems.controller;

import com.razorpay.RazorpayException;
import com.safehiring.ems.api.PaymentAPI;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;
import com.safehiring.ems.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController implements PaymentAPI {

    @Autowired
    private PaymentService paymentService;

    @Override
    public ResponseEntity placeOrder(PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = null;
        try {
            paymentResponse = paymentService.placeOrder(paymentRequest);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<PaymentResponse>(paymentResponse, HttpStatus.OK);
    }
}
