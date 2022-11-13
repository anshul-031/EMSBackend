package com.safehiring.ems.service;


import com.safehiring.ems.service.context.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {

    void sendMail(final AbstractEmailContext email) throws MessagingException;
}