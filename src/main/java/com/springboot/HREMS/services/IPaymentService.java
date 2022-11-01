package com.springboot.HREMS.services;

import com.razorpay.RazorpayException;
import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.OrdersDtoRequest;

public interface IPaymentService {
    ApiResponse createOrder(OrdersDtoRequest request) throws RazorpayException;
}
