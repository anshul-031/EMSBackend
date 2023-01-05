package com.safehiring.ems.service.impl;

import com.safehiring.ems.constant.EmsConstants;
import com.safehiring.ems.exception.EmsException;
import com.safehiring.ems.jpa.data.Order;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.OrderRepository;
import com.safehiring.ems.jpa.repository.UserRepository;
import com.safehiring.ems.service.OrderService;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.util.Signature;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;


/**
 *
 * @author Rais Alam
 *
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;


    private final UserRepository userRepository;


    private final UserService userService;


    @Override
    @Transactional
    public Order saveOrder(final String razorpayOrderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()->  new UsernameNotFoundException("User '" + userDetails.getUsername() + "' not found."));
        Order order = new Order();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(user.getId());
        order.setVerified(false);
        order.setStatus("created");
        order.setCreatedDate(LocalDateTime.now());
        order.setUpdatedDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
        String errorMsg = null;
        try {
            Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
            String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                order.setRazorpayOrderId(razorpayOrderId);
                order.setRazorpayPaymentId(razorpayPaymentId);
                order.setRazorpaySignature(razorpaySignature);
                orderRepository.save(order);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                final UserDetails userDetails = (UserDetails) auth.getPrincipal();
                userDetails.getAuthorities().forEach(obj ->{
                    String updateRole = "";
                    if(obj.getAuthority().equalsIgnoreCase(EmsConstants.EMPLOYEE_UNPAID_ROLE)) {
                        updateRole = EmsConstants.EMPLOYEE_ROLE;
                    }
                    else if(obj.getAuthority().equalsIgnoreCase(EmsConstants.EMPLOYER_UNPAID_ROLE)) {
                        updateRole = EmsConstants.EMPLOYER_ROLE;
                    }
                    if(!StringUtils.isEmpty(updateRole))
                        userService.updateUserGroup(userDetails.getUsername(), updateRole);
                });

            } else {
                errorMsg = "Payment validation failed: Signature doesn't match. ";
            }
        } catch (Exception e) {
           log.error(e.getLocalizedMessage());
           throw new EmsException(EmsException.INVALID_PAYMENT, "Payment validation failed.");
        }
        return errorMsg;
    }

    @Override
    public List<Order> getOrders(LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByCreatedDateBetweenAndIsVerifiedIsFalse(start, end);
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
