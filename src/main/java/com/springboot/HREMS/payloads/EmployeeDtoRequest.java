package com.springboot.HREMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDtoRequest {
    private String id;
    private String safeHiringRegistration;
    private String panCard;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private Date dob;
    private String graduationYear;
    private String sscYear;
    private String scYear;
    private String collegeName;
    private String schoolName;
    private String address;
    private String permanentAddress;
    private String preferredWorkLocation;
    private String aadhaarNumber;
}
