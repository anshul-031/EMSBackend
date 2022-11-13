package com.safehiring.ems.controller;

import com.safehiring.ems.controller.data.reqest.EmployerRegistrationRequest;
import com.safehiring.ems.exceptio.InvalidTokenException;
import com.safehiring.ems.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/api/register")
@Data
@Slf4j
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/employer")
    public String registerEmployer(@RequestBody @Valid EmployerRegistrationRequest registrationRequest) {
        registrationRequest.setGroup("EMPLOYER");
        userService.register(registrationRequest);
        return "Employer registration successful";
    }

    @PostMapping("/employee")
    public String registerEmployee(@RequestBody @Valid EmployerRegistrationRequest registrationRequest) {
        log.info("Inside registration controller");
        registrationRequest.setGroup("EMPLOYEE");
        userService.register(registrationRequest);
        return "Employee registration successful";
    }

    @PostMapping("/support")
    public String registerSupport(@RequestBody @Valid EmployerRegistrationRequest registrationRequest) {
        log.info("Inside registration controller");
        registrationRequest.setGroup("SUPPORT");
        userService.register(registrationRequest);
        return "Support registration successful";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token) throws InvalidTokenException {
        if (StringUtils.isEmpty(token)) {
            throw new InvalidTokenException("Invalid token");
        }
        userService.verifyUser(token);
        return "Employer verification successful";
    }
}
