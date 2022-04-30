package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository <Company, Long> {

    Company findByEmail(String email);

    Company findByName(String name);

    boolean existsByEmail(String email);

}
