package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    //Getting an admin according to his email
    Admin findByEmail(String email);

    //Checking an admin if exists according to his email
    boolean existsByEmail(String email);

}
