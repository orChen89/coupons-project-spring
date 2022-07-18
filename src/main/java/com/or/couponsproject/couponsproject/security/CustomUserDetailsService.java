package com.or.couponsproject.couponsproject.security;


import com.or.couponsproject.couponsproject.model.Admin;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.repo.AdminRepository;
import com.or.couponsproject.couponsproject.repo.CompanyRepository;
import com.or.couponsproject.couponsproject.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUser(email);
    }

    private User getUser(String email) {

        if (companyRepository.existsByEmail(email)) {
            Company company = companyRepository.findByEmail(email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(company.getRole())));
            return new User(company.getEmail(), String.valueOf(company.getPassword()), authorities);

        } else if (customerRepository.existsByEmail(email)) {
            Customer customer = customerRepository.findByEmail(email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(customer.getRole())));
            return new User(customer.getEmail(), String.valueOf(customer.getPassword()), authorities);

        } else if (adminRepository.existsByEmail(email)) {
            Admin admin = adminRepository.findByEmail(email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(admin.getRole())));
            return new User(admin.getEmail(), String.valueOf(admin.getPassword()), authorities);
        }
        return null;
    }
}

