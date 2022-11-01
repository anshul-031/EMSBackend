package com.springboot.HREMS.serviceImpl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.OrderDtoResponse;
import com.springboot.HREMS.payloads.OrdersDtoRequest;
import com.springboot.HREMS.services.IPaymentService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PaymentServicesImpl implements IPaymentService {
    private final ApiResponse response;
    @Override
    public ApiResponse createOrder(OrdersDtoRequest request) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(request.getKey(), request.getSecret());
        JSONObject orderRequest = new JSONObject();
        int amount=Integer.parseInt(request.getAmount())*100;
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt#1");
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        notes.put("notes_key_1", "Tea, Earl Grey, Hot");
        orderRequest.put("notes", notes);

        Order order = razorpay.orders.create(orderRequest);
        OrderDtoResponse data=new OrderDtoResponse();
        data.setOrder_id(order.get("id"));
        data.setAmount(order.get("amount").toString());
        data.setCurrency(order.get("currency"));
        /*data.setEmail(request.getEmail());*/
        data.setStatus(order.get("status"));
        /*data.setName(request.getCustomerName());*/
        response.setData(data);
        response.setStatus(HttpStatus.OK);
        return response;
    }
}
