package com.springboot.HREMS.repository;

import com.springboot.HREMS.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<Users,String> {
    Users findByEmailId(String emailId);
}
