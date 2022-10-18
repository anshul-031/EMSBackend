package com.springboot.HREMS.utils;

import com.springboot.HREMS.models.Users;
import com.springboot.HREMS.repository.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private IUsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Users findByEmail =usersRepository.findByEmailId(emailId);
        if(findByEmail==null){
            return null;
        } else if (findByEmail.getEmailId().equals(emailId)) {
            return new User(findByEmail.getEmailId(), findByEmail.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }
}
