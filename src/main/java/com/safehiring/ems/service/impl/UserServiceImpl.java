package com.safehiring.ems.service.impl;

import com.safehiring.ems.controller.data.reqest.EmployerRegistrationRequest;
import com.safehiring.ems.exceptio.InvalidTokenException;
import com.safehiring.ems.exceptio.UserAlreadyExistsException;
import com.safehiring.ems.jpa.data.Group;
import com.safehiring.ems.jpa.data.SecureToken;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.GroupRepository;
import com.safehiring.ems.jpa.repository.SecureTokenRepository;
import com.safehiring.ems.jpa.repository.UserRepository;
import com.safehiring.ems.service.EmailService;
import com.safehiring.ems.service.SecurityTokenService;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.service.context.AccountVerificationEmailContext;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityTokenService securityTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    private final SecurityTokenService secureTokenService;
    private final GroupRepository groupRepository;

    @Value("${site.base.url.https}")
    private String baseUrl;

    @Override
    public void register(EmployerRegistrationRequest registrationRequest) {
        if (checkIfUserExists(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registrationRequest, userEntity);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        updateUserGroup(userEntity, registrationRequest.getGroup());
        userRepository.save(userEntity);
        sendRegistrationConfirmationEmail(userEntity);

    }

    private void updateUserGroup(UserEntity entity, String code) {
        Group group = groupRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Group " + code + " doesn't exists"));
        entity.addUserGroups(group);

    }

    @Override
    public boolean checkIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void sendRegistrationConfirmationEmail(UserEntity user) {
        SecureToken secureToken = securityTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseUrl, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        UserEntity user = userRepository.findById(secureToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        user.setAccountVerified(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return true;
    }


}
