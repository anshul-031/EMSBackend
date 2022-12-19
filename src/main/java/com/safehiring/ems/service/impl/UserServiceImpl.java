package com.safehiring.ems.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.exception.UserAlreadyExistsException;
import com.safehiring.ems.jpa.data.Group;
import com.safehiring.ems.jpa.data.SecureToken;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.GroupRepository;
import com.safehiring.ems.jpa.repository.SecureTokenRepository;
import com.safehiring.ems.jpa.repository.UserRepository;
import com.safehiring.ems.model.request.EmployerRegistrationRequest;
import com.safehiring.ems.service.EmailService;
import com.safehiring.ems.service.SecurityTokenService;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.service.context.AccountVerificationEmailContext;

@Service
@RequiredArgsConstructor
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
    public void register(final EmployerRegistrationRequest registrationRequest) {
        if (!"INDIA".equalsIgnoreCase(registrationRequest.getCountry())) {
            throw new IllegalArgumentException("Country Not allowed");
        }
        if (this.checkIfUserExists(registrationRequest.getEmail())) {
            throw new UserAlreadyExistsException("User already exists for this email");
        }
        final UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registrationRequest, userEntity);
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        this.updateUserGroup(userEntity, registrationRequest.getGroup());
        this.userRepository.save(userEntity);
        this.sendRegistrationConfirmationEmail(userEntity);

    }

    @Override
    public void updateUserGroup(String userEmail, String code) {
        System.out.println("Updating "+userEmail+" Group = "+code);
        UserEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User does not exists"));
        final Group group = this.groupRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Group " + code + " doesn't exists"));
        userEntity.getUserGroups().clear();
        userEntity.addUserGroups(group);
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return  userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User does not exists"));

    }

    private void updateUserGroup(final UserEntity entity, final String code) {
        final Group group = this.groupRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Group " + code + " doesn't exists"));
        entity.addUserGroups(group);

    }

    @Override
    public boolean checkIfUserExists(final String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void sendRegistrationConfirmationEmail(final UserEntity user) {
        final SecureToken secureToken = this.securityTokenService.createSecureToken();
        secureToken.setUser(user);
        this.secureTokenRepository.save(secureToken);
        final AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(this.baseUrl, secureToken.getToken());
        try {
            this.emailService.sendMail(emailContext);
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean verifyUser(final String token) throws InvalidTokenException {
        final SecureToken secureToken = this.secureTokenService.findByToken(token);
        final UserEntity user = this.userRepository.findById(secureToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
        user.setAccountVerified(true);
        this.userRepository.save(user);

        this.secureTokenService.removeToken(secureToken);
        return true;
    }


}
