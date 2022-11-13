package com.safehiring.ems.security;

import com.safehiring.ems.exceptio.CustomAccessDeniedHandler;
import com.safehiring.ems.exceptio.InvalidTokenException;
import com.safehiring.ems.service.impl.UserDetailServiceImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;

@EqualsAndHashCode(callSuper = true)
@EnableWebSecurity
@Data
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    @Resource
    UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/v1/api/register/**").permitAll()
                .antMatchers("/v3/api-docs/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/register/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/api/offer/opt/**").hasAnyAuthority("ADMIN")
                .antMatchers("/v1/api/employer/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                .antMatchers("/v1/api/employee/**").hasAnyAuthority("EMPLOYEE", "ADMIN")
                .antMatchers("/v1/api/offer/view/**").hasAnyAuthority("EMPLOYER", "ADMIN", "SUPPORT")
                .antMatchers("/v1/api/offer/opt/**").hasAnyAuthority("EMPLOYER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(authenticationProvider());
        /**
         auth.inMemoryAuthentication()
         .withUser("admin")
         .password("{noop}123456")
         .authorities("EMPLOYER");
         **/

    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
