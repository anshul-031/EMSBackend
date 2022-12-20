package com.safehiring.ems.controller;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.razorpay.Payment;
import com.safehiring.ems.constant.EmsConstants;
import com.safehiring.ems.service.OrderService;
import com.safehiring.ems.service.impl.ScheduledTasks;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import static java.net.URLDecoder.decode;

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

    private final ScheduledTasks scheduledTasks;

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

    @PostMapping("/webhook")
    public String paymentWebHook(@RequestBody final String message) {
        JSONObject jsonObject = new JSONObject(message);
        JSONObject paymentEntity = jsonObject.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");
        String data = String.format(messageTemplate, jsonObject.get("event"), paymentEntity.get("order_id"),paymentEntity.get("status"), paymentEntity.get("id"), paymentEntity.getInt("amount")/100, paymentEntity.get("email"));
        jwtUtil.sendSlackNotification(data);
        return "Ok";

    }

    @PostMapping("/command")
    public String slashCommand(@RequestBody final String message) throws UnsupportedEncodingException {
        Map<String,String> map = Pattern.compile("\\s*&\\s*")
                .splitAsStream(message.trim())
                .map(s -> s.split("=", 2))
                .collect(Collectors.toMap(a -> a[0], a -> a.length>1? a[1]: ""));
        String commandText = decode(map.get("text"), StandardCharsets.UTF_8.name());
        if(commandText.contains("|") && commandText.contains("@")) {
            String email = commandText.split("\\|")[1].replace(">","");
            String result = "";
            try{
                List<Payment> payments = scheduledTasks.getPaymentsByEmail(email);
                result = "```"+(payments .size() > 0? payments.toString(): "No payments found") +"```";
            } catch (Exception e){
                result = e.getMessage();
            }
            return result;

        } else {
            return "Please provide valid email";
        }


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
        final Set<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        final UserVo user = new UserVo();
        user.setUsername(username);
        user.setUserpwd(userpwd);
        user.setRoles(roles);
        final String token = this.jwtUtil.generateToken(user);
        return new JwtResponse(token);
    }

    private static final String messageTemplate = "{\"blocks\":[{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"------------------------\\nYou have a new payment\"}},{\"type\":\"section\",\"fields\":[{\"type\":\"mrkdwn\",\"text\":\"*Event:*\\n`%s`\"},{\"type\":\"mrkdwn\",\"text\":\"*OrderId:*\\n`%s`\"},{\"type\":\"mrkdwn\",\"text\":\"*Status:*\\n`%s`\"},{\"type\":\"mrkdwn\",\"text\":\"*PaymeId:*\\n`%s`\"},{\"type\":\"mrkdwn\",\"text\":\"*Amount:*\\n`%s`\"},{\"type\":\"mrkdwn\",\"text\":\"*User:*\\n%s\"}]}]}";

    public static void main(String[] args) {
        try {
            String message = "token=HKIwcf8B3yZGfIxtQDtbZz0J&team_id=T04FQS76M4K&team_domain=slack-thv6428&channel_id=C04FM16970E&channel_name=razor-pay&user_id=U04FR04TZPU&user_name=rais7jmi&command=%2Fdetail&text=%3Cmailto%3Arais9jmi%40gmail.com%7Crais9jmi%40gmail.com%3E&api_app_id=A04FM4XE0T0&is_enterprise_install=false&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT04FQS76M4K%2F4540307947523%2FLdZ3nzmQh3vArSwFJYtqO2xf&trigger_id=4537476155125.4534891225155.cb77bd6e778ab2d5bed19a898466eccc";
            Map<String,String> map = Pattern.compile("\\s*&\\s*")
                    .splitAsStream(message.trim())
                    .map(s -> s.split("=", 2))
                    .collect(Collectors.toMap(a -> a[0], a -> a.length>1? a[1]: ""));
            System.out.println(map);
            String commandText = decode(map.get("text"), StandardCharsets.UTF_8.name());
            System.out.println("command text = "+commandText);
            System.out.println("commandText.contains(\"\\\\|\") "+commandText.contains("|"));
            if(commandText.contains("|") && commandText.contains("@")) {
                String email = commandText.split("\\|")[1].replace(">","");
                System.out.println("email  === "+email);
            }




            if(map.get("user_name").equalsIgnoreCase("rais7_jmi")){

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
