package com.safehiring.ems.service.impl;

import com.safehiring.ems.TestConfig;
import com.safehiring.ems.jpa.data.Order;
import com.safehiring.ems.jpa.data.UserEntity;
import com.safehiring.ems.jpa.repository.GroupRepository;
import com.safehiring.ems.jpa.repository.OrderRepository;
import com.safehiring.ems.jpa.repository.SecureTokenRepository;
import com.safehiring.ems.jpa.repository.UserRepository;
import com.safehiring.ems.service.EmailService;
import com.safehiring.ems.service.OrderService;
import com.safehiring.ems.service.SecurityTokenService;
import com.safehiring.ems.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;


import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private SecurityTokenService securityTokenService;
    @Mock
    private SecureTokenRepository secureTokenRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private SecurityTokenService secureTokenService;
    @Mock
    private GroupRepository groupRepository;

    @Mock
    private OrderRepository orderRepository;


    private static final String USER_EMAIL = "rais7jmi@gmail.com";
    private static final String ORDER_ID = "orderId1";
    private UserService userService;

    private OrderService orderService;

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
        userService = new UserServiceImpl(userRepository, passwordEncoder, securityTokenService, secureTokenRepository, emailService, secureTokenService, groupRepository);
        orderService = new OrderServiceImpl(orderRepository, userRepository, userService);
    }

    @DisplayName("OrderServiceImpl::saveOrder should be saved successful")
    @Test
    void saveOrder() {
        Mockito.when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(getUser()));
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getPrincipal()).thenReturn(getUserDetails());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        orderService.saveOrder(ORDER_ID);
        verify(orderRepository, times(1)).save(Mockito.any(Order.class));

    }

    @Test
    void validateAndUpdateOrder() {
    }


    private UserEntity getUser() {
        UserEntity user = new UserEntity();
        user.setEmail(USER_EMAIL);
        user.setId(1L);
        user.setPassword("password");
        user.setUserGroups(new HashSet<>());
        user.setAccountVerified(true);
        user.setMobile(9911991199L);
        return user;

    }

    private UserDetails getUserDetails() {
        UserEntity user = getUser();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isAccountVerified(), true, true, true, new ArrayList<>());
    }
}