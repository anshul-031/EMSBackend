package com.safehiring.ems.service;

import com.safehiring.ems.jpa.data.Order;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    Order saveOrder(String razorpayOrderId);

    String validateAndUpdateOrder(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, String secret);
}
