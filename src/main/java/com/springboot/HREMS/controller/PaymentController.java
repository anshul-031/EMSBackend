package com.springboot.HREMS.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.HREMS.payloads.OrdersDtoRequest;
import com.springboot.HREMS.services.IPaymentService;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/payment/")
@AllArgsConstructor
public class PaymentController {
    private final IPaymentService service;
    private final Environment environment;
    @PostMapping("create")
    public ResponseEntity<?> createOrder(@RequestBody OrdersDtoRequest request){
        try {
            request.setKey(environment.getProperty("razorpay.key"));
            request.setSecret(environment.getProperty("razorpay.secret"));
            return ResponseEntity.ok(service.createOrder(request));
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }


    }
}
