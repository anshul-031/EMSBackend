package com.safehiring.ems.controller;

import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.safehiring.ems.constant.EmsConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safehiring.ems.exception.InvalidTokenException;
import com.safehiring.ems.exception.InvalidUserException;
import com.safehiring.ems.model.request.EmployerRegistrationRequest;
import com.safehiring.ems.model.request.JwtRequest;
import com.safehiring.ems.model.request.JwtResponse;
import com.safehiring.ems.model.request.UserVo;
import com.safehiring.ems.service.UserService;
import com.safehiring.ems.service.impl.UserDetailServiceImpl;
import com.safehiring.ems.util.JwtUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/register")
@Data
@Slf4j
public class RegistrationController {

    private final UserService userService;
    private final UserDetailServiceImpl userAuthService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/employer")
    public String registerEmployer(@RequestBody @Valid final EmployerRegistrationRequest registrationRequest) {
        registrationRequest.setGroup(EmsConstants.EMPLOYER_UNPAID_ROLE);
        this.userService.register(registrationRequest);
        return "Employer registration successful";
    }

    @PostMapping("/employee")
    public String registerEmployee(@RequestBody @Valid final EmployerRegistrationRequest registrationRequest) {
        log.info("Inside registration controller");
        registrationRequest.setGroup(EmsConstants.EMPLOYEE_UNPAID_ROLE);
        this.userService.register(registrationRequest);
        return "Employee registration successful";
    }

    @PostMapping("/support")
    public String registerSupport(@RequestBody @Valid final EmployerRegistrationRequest registrationRequest) {
        log.info("Inside registration controller");
        registrationRequest.setGroup("SUPPORT");
        this.userService.register(registrationRequest);
        return "Support registration successful";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam final String token) throws InvalidTokenException {
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException("Invalid token");
        }
        this.userService.verifyUser(token);
        return "Employer verification successful";
    }

    @GetMapping("/health")
    public String health() throws InvalidTokenException {
        return "Ok";
    }

    @PostMapping("/signin")
    public JwtResponse generateJwtToken(@RequestBody final JwtRequest jwtRequest) {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getUserpwd()));
        } catch (final DisabledException e) {
            /***
             * TO DO VERY HIGH PRIORITY
             * DisabledException not working. That means If a user is inactive, Still we are not geeting this
             * DisabledException error
             */
            throw new InvalidUserException("User Inactive");
        } catch (final BadCredentialsException e) {
            throw new InvalidUserException("Invalid Credentials");
        }

        final UserDetails userDetails = this.userAuthService.loadUserByUsername(jwtRequest.getUsername());
        final String username = userDetails.getUsername();
        final String userpwd = userDetails.getPassword();
        final Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        final UserVo user = new UserVo();
        user.setUsername(username);
        user.setUserpwd(userpwd);
        user.setRoles(roles);
        final String token = this.jwtUtil.generateToken(user);
        return new JwtResponse(token);
    }
}
