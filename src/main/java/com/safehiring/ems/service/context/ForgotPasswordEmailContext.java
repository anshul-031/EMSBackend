package com.safehiring.ems.service.context;

import com.safehiring.ems.jpa.data.UserEntity;
import org.springframework.web.util.UriComponentsBuilder;

public class ForgotPasswordEmailContext extends AbstractEmailContext {

    private String token;


    @Override
    public <T> void init(T context) {
        //we can do any common configuration setup here
        // like setting up some base URL and context
        UserEntity user = (UserEntity) context;
        put("firstName", user.getCompanyName());
        setTemplateLocation("emails/forgot-password");
        setSubject("Forgotten Password");
        setFrom("no-reply@safehiring.com");
        setTo(user.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL).path("/password/change").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}