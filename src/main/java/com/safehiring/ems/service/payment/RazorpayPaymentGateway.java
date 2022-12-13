package com.safehiring.ems.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.safehiring.ems.model.request.OrderVo;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;
import org.json.JSONObject;

public class RazorpayPaymentGateway implements PaymentGateway {

    public PaymentResponse placeOrder(PaymentRequest paymentRequest) throws RazorpayException, JsonProcessingException {

            RazorpayClient razorpay = new RazorpayClient("rzp_test_BpeSJ7UdmHAXcP", "28AibsX10gwGn5yLEuYiTAXi");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", paymentRequest.getAmount()); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid");

            Order order = razorpay.orders.create(orderRequest);

            OrderVo orderVo = new ObjectMapper().readValue(order.toString(), OrderVo.class);

            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setOrderId(orderVo.getId());
            paymentResponse.setStatus(orderVo.getStatus());
            return paymentResponse;
    }

}
