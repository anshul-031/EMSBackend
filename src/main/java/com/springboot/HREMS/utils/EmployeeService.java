package com.springboot.HREMS.utils;
import com.springboot.HREMS.models.Employees;
import com.springboot.HREMS.repository.IEmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private IEmployeesRepository employeesRepository;
    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Employees findByEmail =employeesRepository.findByEmail(emailId);
        if(findByEmail==null){
            return null;
        } else if (findByEmail.getEmail().equals(emailId)) {
            return new User(findByEmail.getEmail(), findByEmail.getPassword(), new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Username is not valid");
        }
    }
}
