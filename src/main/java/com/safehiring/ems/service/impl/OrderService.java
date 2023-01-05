package com.safehiring.ems.service.impl;

import com.safehiring.ems.constant.EmsConstants;
import com.safehiring.ems.jpa.data.Group;
import com.safehiring.ems.jpa.data.Order;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.OrderRepository;
import com.safehiring.ems.jpa.repository.UserRepository;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.util.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 
 * @author Chinna
 *
 */
@Slf4j
@Service
public class OrderService {
 
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
 
    @Transactional
    public Order saveOrder(final String razorpayOrderId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) auth.getPrincipal();
        UserEntity user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(()->  new UsernameNotFoundException("User '" + userDetails.getUsername() + "' not found."));
        Order order = new Order();
        order.setRazorpayOrderId(razorpayOrderId);
        order.setUserId(user.getId());
        return orderRepository.save(order);
    }
 
    @Transactional
    public String validateAndUpdateOrder(final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final String secret) {
        String errorMsg = null;
        try {
            Order order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
            // Verify if the razorpay signature matches the generated one to
            // confirm the authenticity of the details returned
            String generatedSignature = Signature.calculateRFC2104HMAC(order.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                order.setRazorpayOrderId(razorpayOrderId);
                order.setRazorpayPaymentId(razorpayPaymentId);
                order.setRazorpaySignature(razorpaySignature);
                orderRepository.save(order);
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                final UserDetails userDetails = (UserDetails) auth.getPrincipal();
                userDetails.getAuthorities().stream().forEach(obj ->{
                    String updateRole = "";
                    if(obj.getAuthority().equalsIgnoreCase(EmsConstants.EMPLOYEE_UNPAID_ROLE)) {
                        updateRole = EmsConstants.EMPLOYEE_ROLE;
                    }
                    else if(obj.getAuthority().equalsIgnoreCase(EmsConstants.EMPLOYER_UNPAID_ROLE)) {
                        updateRole = EmsConstants.EMPLOYER_ROLE;
                    }
                    userService.updateUserGroup(userDetails.getUsername(), updateRole);
                });

            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            log.error("Payment validation failed.", e);
            errorMsg = e.getMessage();
        }
        return errorMsg;
    }

}
