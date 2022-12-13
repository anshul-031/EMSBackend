package com.safehiring.ems.service.payment;

import com.safehiring.ems.model.PaymentGatewayEnum;

/**
 *
 * Factory class that will return the Payment Gateway Method based on the
 * payment method request by user
 *
 * */
public class PaymentGatewayFactory {

    /*
    * This will return the payment method based on Payment Gateway type selected*/
    public PaymentGateway getPaymentGateway(PaymentGatewayEnum paymentGatewayEnum) {

        if (PaymentGatewayEnum.RAZORPAY.equals(paymentGatewayEnum)) {
            return new RazorpayPaymentGateway();
        }
        return new RazorpayPaymentGateway();
    }
}
