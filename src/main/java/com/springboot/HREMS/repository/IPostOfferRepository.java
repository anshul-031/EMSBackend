package com.springboot.HREMS.repository;

import com.springboot.HREMS.models.EmployeeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostOfferRepository extends JpaRepository<EmployeeOffer,String> {
    List<EmployeeOffer> findByPanNumber(String panNumber);
}
