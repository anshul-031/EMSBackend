package com.safehiring.ems.service.impl;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.safehiring.ems.config.RazorPayClientConfig;
import com.safehiring.ems.jpa.data.Order;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.service.OrderService;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {

	private final OrderService orderService;

	private final RazorpayClient client;

	private final RazorPayClientConfig razorPayClientConfig;

	private final UserService userService;

	@Autowired
	JwtUtil jwtUtil;


	public ScheduledTasks(RazorPayClientConfig razorpayClientConfig, OrderService orderService, UserService userService) throws RazorpayException {
		this.razorPayClientConfig = razorpayClientConfig;
		this.client = new RazorpayClient(razorpayClientConfig.getKey(), razorpayClientConfig.getSecret());
		this.orderService = orderService;
		this.userService = userService;
	}

	@Scheduled(fixedRate = 50000)
	public void verifyPayments(){

		LocalDateTime start = LocalDateTime.now().minusHours(1);
		LocalDateTime end = LocalDateTime.now();

		List<Order> orders = orderService.getOrders(start, end);
		orders.forEach(order -> {
			try {
				List<Payment>  payments = client.orders.fetchPayments(order.getRazorpayOrderId());
				payments.forEach(payment -> {
					if(!StringUtils.isEmpty(payment.get("status"))) {
						order.setStatus(payment.get("status"));
						order.setVerified(payment.get("status").equals("captured") || payment.get("status").equals("failed"));
						order.setUpdatedDate(LocalDateTime.now());
						orderService.updateOrder(order);

						UserEntity user = userService.getUserById(order.getUserId());
						if (!user.isPaymentVerified()) {
							user.setPaymentVerified(payment.get("status").equals("captured"));
							userService.updateUser(user);
							//jwtUtil.sendSlackNotification("Payment for "+user.getEmail()+" with order Id `"+order.getRazorpayOrderId()+"` is `"+payment.get("status")+"`");
						}
					}

				});
			} catch (RazorpayException e) {
				throw new RuntimeException(e);
			}


		});
	}
}