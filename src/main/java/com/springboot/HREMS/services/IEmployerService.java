package com.springboot.HREMS.services;

import com.springboot.HREMS.payloads.ApiResponse;
import com.springboot.HREMS.payloads.EmployersDtoRequest;

import javax.mail.MessagingException;

public interface IEmployerService {
    ApiResponse createUsers(EmployersDtoRequest request);
    ApiResponse loginUsers(String emailId,String password);
    ApiResponse forgetPassword(String emailId) throws MessagingException;
    ApiResponse resetPassword(String password,String id);
    void SendEmail(String to,String subject,String text) throws MessagingException;
}
