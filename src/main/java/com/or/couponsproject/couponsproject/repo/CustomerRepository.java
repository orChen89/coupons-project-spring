package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long> {

    Customer findByEmail(String email);

    boolean existsByEmail(String email);

}
