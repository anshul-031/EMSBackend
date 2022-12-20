package com.safehiring.ems.jpa.repository;

import com.safehiring.ems.jpa.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
 
    Order findByRazorpayOrderId(String orderId);
    List<Order> findByCreatedDateBetweenAndIsVerifiedIsFalse(LocalDateTime start, LocalDateTime end);
    List<Order> findByUserId(Long userId);
}