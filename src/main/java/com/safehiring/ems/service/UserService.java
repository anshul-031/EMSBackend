package com.safehiring.ems.service;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.model.request.EmployerRegistrationRequest;

public interface UserService {
    void register(final EmployerRegistrationRequest registrationRequest);
    void updateUserGroup(final String userEmail, String userGroup);

    UserEntity getUserByEmail(final String userEmail);

    UserEntity getUserById(final Long id);

    boolean checkIfUserExists(final String email);

    void sendRegistrationConfirmationEmail(UserEntity user);

    boolean verifyUser(String token) throws InvalidTokenException;

    void updateUser(final UserEntity userEntity);
}
