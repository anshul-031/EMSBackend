package com.springboot.HREMS.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_employees_offer")
public class EmployeeOffer {
    @Id
    private String id;
    private String name;
    private String company;
    private String offerReceivedDate;
    private String joiningDate;
    private String jobTitle;
    private String ctc;
    private String panNumber;
}
