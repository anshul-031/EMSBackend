package com.springboot.HREMS.services;

import com.springboot.HREMS.models.EmployeeOffer;
import com.springboot.HREMS.models.Employees;
import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.EmployeeDtoRequest;
import com.springboot.HREMS.payloads.EmployersDtoRequest;
import com.springboot.HREMS.payloads.OfferDtoRequest;

import javax.mail.MessagingException;
import java.util.List;

public interface IEmployeeService {
    ApiResponse createUsers(EmployeeDtoRequest request);
    ApiResponse loginUsers(String emailId,String password);
    ApiResponse forgetPassword(String emailId) throws MessagingException;
    ApiResponse resetPassword(String password,String id);
    void SendEmail(String to,String subject,String text) throws MessagingException;
    List<EmployeeOffer> SearchByPan(String PANNumber);
    ApiResponse createEmployeesOffer(OfferDtoRequest request);
}
