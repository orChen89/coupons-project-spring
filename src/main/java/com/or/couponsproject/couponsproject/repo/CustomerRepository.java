package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

    //Getting a customer according to his email
    Customer findByEmail(String email);

    //Checking a customer if exists according to his email
    boolean existsByEmail(String email);

}
