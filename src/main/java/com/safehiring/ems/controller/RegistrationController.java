package com.safehiring.ems.controller;

import javax.validation.Valid;

import com.safehiring.ems.exception.InvalidUserException;
import com.safehiring.ems.model.request.JwtRequest;
import com.safehiring.ems.model.request.JwtResponse;
import com.safehiring.ems.model.request.UserVo;
import com.safehiring.ems.service.impl.UserDetailServiceImpl;
import com.safehiring.ems.util.JwtUtil;
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
import com.safehiring.ems.model.request.EmployerRegistrationRequest;
import com.safehiring.ems.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Collectors;

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
        registrationRequest.setGroup("EMPLOYER");
        this.userService.register(registrationRequest);
        return "Employer registration successful";
    }

    @PostMapping("/employee")
    public String registerEmployee(@RequestBody @Valid final EmployerRegistrationRequest registrationRequest) {
        log.info("Inside registration controller");
        registrationRequest.setGroup("EMPLOYEE");
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

    @PostMapping("/signin")
    public JwtResponse generateJwtToken(@RequestBody JwtRequest jwtRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getUserpwd()));
        } catch (DisabledException e) {
            throw new InvalidUserException("User Inactive");
        } catch (BadCredentialsException e) {
            throw new InvalidUserException("Invalid Credentials");
        }
        UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
        String username = userDetails.getUsername();
        String userpwd = userDetails.getPassword();
        Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
                .collect(Collectors.toSet());
        UserVo user = new UserVo();
        user.setUsername(username);
        user.setUserpwd(userpwd);
        user.setRoles(roles);
        String token = jwtUtil.generateToken(user);
        return new JwtResponse(token);
    }
}
