package com.springboot.HREMS.models;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_employees")
public class Employees {
    @Id
    private String id;
    private String safeHiringRegistration;
    private String panCard;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    @JsonFormat(pattern = "yyyy/MM/dd")
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
