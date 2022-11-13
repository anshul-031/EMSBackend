package com.safehiring.ems.service;

import com.safehiring.ems.controller.data.reqest.EmployerRegistrationRequest;
import com.safehiring.ems.exceptio.InvalidTokenException;
import com.safehiring.ems.jpa.data.UserEntity;

public interface UserService {
    void register(final EmployerRegistrationRequest registrationRequest);

    boolean checkIfUserExists(final String email);

    void sendRegistrationConfirmationEmail(UserEntity user);

    boolean verifyUser(String token) throws InvalidTokenException;
}
