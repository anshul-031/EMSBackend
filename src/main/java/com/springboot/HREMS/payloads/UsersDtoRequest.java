package com.springboot.HREMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDtoRequest {
    private String id;
    private String username;
    private String companyName;
    private String cinNumber;
    private String website;
    private String mobile;
    private String emailId;
    private String password;
    private String newPassword;
    private String token;
}
