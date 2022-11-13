package com.safehiring.ems.service.context;

import com.safehiring.ems.jpa.data.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class AccountVerificationEmailContext extends AbstractEmailContext {

    private String token;


    @Override
    public <T> void init(T context) {

        UserEntity user = (UserEntity) context;
        put("firstName", user.getCompanyName());
        setTemplateLocation("emails/email-verification");
        setSubject("Welcome to EMS Complete your registration");
        setFrom("no-reply@safehiring.com");
        setTo(user.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL).path("/register/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}