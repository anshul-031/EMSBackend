package com.safehiring.ems.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.safehiring.ems.config.RazorPayClientConfig;
import com.safehiring.ems.model.request.OrderRequest;
import com.safehiring.ems.model.request.OrderResponse;
import com.safehiring.ems.model.response.PaymentResponse;
import com.safehiring.ems.service.OrderService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
 
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

/**
 * 
 * @author Chinna
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/order")
@Data
@Slf4j
public class OrderController {
 
    private final RazorpayClient client;
 
    private final RazorPayClientConfig razorPayClientConfig;
 

    private final OrderService orderService;


    public OrderController(RazorPayClientConfig razorpayClientConfig, OrderService orderService) throws RazorpayException {
        this.razorPayClientConfig = razorpayClientConfig;
        this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
        this.orderService = orderService;
    }


    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        OrderResponse razorPay;
        try {
            // The transaction amount is expressed in the currency subunit, such
            // as paise (in case of INR)
            String amountInPaise = convertRupeeToPaise(orderRequest.getAmount());
            // Create an order in RazorPay and get the order id
            Order order = createRazorPayOrder(amountInPaise);
            razorPay = getOrderResponse(order.get("id"), amountInPaise);
            // Save order in the database
            orderService.saveOrder(razorPay.getRazorpayOrderId());
        } catch (RazorpayException e) {
            log.error("Exception while create payment order", e);
            return new ResponseEntity<>(new ApiResponse(), HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok(razorPay);
    }
 
    @PutMapping("/order")
    public ResponseEntity<?> updateOrder(@RequestBody PaymentResponse paymentResponse) {
        String errorMsg = orderService.validateAndUpdateOrder(paymentResponse.getRazorpayOrderId(), paymentResponse.getRazorpayPaymentId(), paymentResponse.getRazorpaySignature(),
                razorPayClientConfig.getSecret());
        if (errorMsg != null) {
            return new ResponseEntity<>(new ApiResponse(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new ApiResponse());
    }
 
    private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
        OrderResponse razorPay = new OrderResponse();
        razorPay.setApplicationFee(amountInPaise);
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(razorPayClientConfig.getKey());
        return razorPay;
    }
 
    private Order createRazorPayOrder(String amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        return client.orders.create(options);
    }
 
    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }
}