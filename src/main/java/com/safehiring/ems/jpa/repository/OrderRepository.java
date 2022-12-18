package com.safehiring.ems.jpa.repository;

import com.safehiring.ems.jpa.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
 
    Order findByRazorpayOrderId(String orderId);
}