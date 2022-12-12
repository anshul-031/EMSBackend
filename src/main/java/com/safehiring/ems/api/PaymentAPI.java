package com.safehiring.ems.api;

import com.safehiring.ems.model.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin("*")
@RequestMapping("/v1/payment")
public interface PaymentAPI {

    @PostMapping("/placeOrder")
    ResponseEntity placeOrder(@RequestBody PaymentRequest paymentRequest);

}
