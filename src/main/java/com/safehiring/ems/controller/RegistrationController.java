package com.safehiring.ems.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/register")
@Data
@Slf4j
public class RegistrationController {

    private final UserService userService;

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
}
