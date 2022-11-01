package com.springboot.HREMS.repository;

import com.springboot.HREMS.models.Employers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployerRepository extends JpaRepository<Employers,String> {
    Employers findByEmailId(String emailId);
}
