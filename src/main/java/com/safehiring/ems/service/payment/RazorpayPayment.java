package com.safehiring.ems.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.safehiring.ems.model.request.OrderVo;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;
import org.json.JSONObject;

public class RazorpayPayment implements PaymentGateway {

    public PaymentResponse placeOrder(PaymentRequest paymentRequest) throws RazorpayException {

            RazorpayClient razorpay = new RazorpayClient("rzp_test_BpeSJ7UdmHAXcP", "28AibsX10gwGn5yLEuYiTAXi");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", paymentRequest.getAmount()); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "order_rcptid");

            Order order = razorpay.orders.create(orderRequest);

            OrderVo orderVo = new ObjectMapper().convertValue(order.toJson(), OrderVo.class);

            return null;
    }

}
