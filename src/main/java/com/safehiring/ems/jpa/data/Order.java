package com.safehiring.ems.jpa.data;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
/**
 * The persistent class for the user_order database table.
 * 
 */
@Entity
@Table(name = "USER_ORDER")
@NoArgsConstructor
@Getter
@Setter
public class Order implements Serializable {
 
    /**
     * 
     */
    private static final long serialVersionUID = 65981149772133526L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
 @Column(name = "user_id")
    private Long userId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;
 
}