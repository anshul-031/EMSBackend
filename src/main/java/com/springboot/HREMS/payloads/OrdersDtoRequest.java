package com.springboot.HREMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "razorpay")
public class OrdersDtoRequest {
    private String customerName;
    private String key;
    private String secret;
    private String email;
    private String phoneNumber;
    private String amount;
    private String currency;
}
