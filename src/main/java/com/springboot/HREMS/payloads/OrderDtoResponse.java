package com.springboot.HREMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class OrderDtoResponse {
    private String order_id;
    private String status;
    private String currency;
    private String amount;
    private String name;
    private String email;
}
