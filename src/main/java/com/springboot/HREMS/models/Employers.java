package com.springboot.HREMS.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_employers")
public class Employers {
    @Id
    private String id;
    private String username;
    private String companyName;
    private String cinNumber;
    private String website;
    private String mobile;
    private String emailId;
    private String password;
}
