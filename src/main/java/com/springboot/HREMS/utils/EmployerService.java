package com.springboot.HREMS.utils;

import com.springboot.HREMS.models.Employers;
import com.springboot.HREMS.repository.IEmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class EmployerService implements UserDetailsService {
    @Autowired
    private IEmployerRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Employers findByEmail =usersRepository.findByEmailId(emailId);
        if(findByEmail==null){
            return null;
        } else if (findByEmail.getEmailId().equals(emailId)) {
            return new User(findByEmail.getEmailId(), findByEmail.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }
}
