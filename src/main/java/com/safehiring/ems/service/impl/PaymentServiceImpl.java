package com.safehiring.ems.service.impl;

import com.razorpay.RazorpayException;
import com.safehiring.ems.model.PaymentGatewayEnum;
import com.safehiring.ems.model.request.PaymentRequest;
import com.safehiring.ems.model.response.PaymentResponse;
import com.safehiring.ems.service.PaymentService;
import com.safehiring.ems.service.payment.PaymentGateway;
import com.safehiring.ems.service.payment.PaymentGatewayFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse placeOrder(PaymentRequest paymentRequest) throws RazorpayException {

        PaymentGatewayFactory paymentGatewayFactory = new PaymentGatewayFactory();
        PaymentGateway paymentGateway = paymentGatewayFactory.getPaymentGateway(PaymentGatewayEnum.RAZORPAY);
        return paymentGateway.placeOrder(paymentRequest);

    }
}
