package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    //Getting a company according to it's email
    Company findByEmail(String email);

    //Getting a company according to it's name
    Company findByName(String name);

    //Checking a company if exists according to it's email
    boolean existsByEmail(String email);

}
