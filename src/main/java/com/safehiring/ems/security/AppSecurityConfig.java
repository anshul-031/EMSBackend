package com.safehiring.ems.security;

import com.safehiring.ems.constant.EmsConstants;
import com.safehiring.ems.exception.CustomAccessDeniedHandler;
import com.safehiring.ems.filter.JwtAuthenticationFilter;
import com.safehiring.ems.service.impl.UserDetailServiceImpl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EqualsAndHashCode(callSuper = true)
@EnableWebSecurity
@Data
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Resource
    UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ApiAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v1/api/register/**", "/v1/api/register/**");
    }
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.cors().and().csrf().disable().authorizeRequests().antMatchers("/v1/api/register/**", "/v1/api/register/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/v1/api/offer/opt/**").hasAnyAuthority("ADMIN")
                .antMatchers("/v1/api/employer/employmentoffer/search").hasAnyAuthority(EmsConstants.EMPLOYER_ROLE, EmsConstants.ADMIN_ROLE)
                 .antMatchers("/v1/api/employer/employmentoffer/**").hasAnyAuthority(EmsConstants.EMPLOYER_ROLE,EmsConstants.EMPLOYER_UNPAID_ROLE, EmsConstants.ADMIN_ROLE)
                 .antMatchers("/v1/api/employee/**").hasAnyAuthority(EmsConstants.EMPLOYEE_ROLE, EmsConstants.ADMIN_ROLE)
                .antMatchers("/v1/api/offer/view/**").hasAnyAuthority(EmsConstants.EMPLOYER_ROLE, EmsConstants.ADMIN_ROLE, EmsConstants.SUPPORT_ROLE)
                .antMatchers("/v1/api/offer/opt/**").hasAnyAuthority(EmsConstants.EMPLOYER_ROLE, EmsConstants.ADMIN_ROLE)
                 .antMatchers("/v1/api/order/**").hasAnyAuthority(EmsConstants.EMPLOYER_UNPAID_ROLE, EmsConstants.EMPLOYEE_UNPAID_ROLE, EmsConstants.EMPLOYER_ROLE, EmsConstants.EMPLOYEE_ROLE)
                 .anyRequest()
                .authenticated().and().exceptionHandling().accessDeniedHandler(this.accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public RegistrationBean jwtAuthFilterRegister(JwtAuthenticationFilter filter) {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

}
