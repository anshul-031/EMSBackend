package com.safehiring.ems.service;

import com.safehiring.ems.jpa.data.Order;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order saveOrder(String razorpayOrderId);

    String validateAndUpdateOrder(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature, String secret);

    List<Order> getOrders(LocalDateTime start, LocalDateTime end);
    void updateOrder(Order order);

    List<Order> getOrdersByUserId(Long userId);
}
