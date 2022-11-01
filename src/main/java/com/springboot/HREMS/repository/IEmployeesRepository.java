package com.springboot.HREMS.repository;

import com.springboot.HREMS.models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeesRepository extends JpaRepository<Employees,String> {
    Employees findByEmail(String email);

    List<Employees> findByPanCard(String PanCard);
}
