package com.or.couponsproject.couponsproject.security;

import com.or.couponsproject.couponsproject.dto.UserDto;
import com.or.couponsproject.couponsproject.model.Admin;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.repo.AdminRepository;
import com.or.couponsproject.couponsproject.repo.CompanyRepository;
import com.or.couponsproject.couponsproject.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtService {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    public UserDto loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUser(email);
    }

    private UserDto getUser(String email) {

        if (companyRepository.existsByEmail(email)) {
            Company company = companyRepository.findByEmail(email);
            return new UserDto(company.getId(), company.getEmail(), company.getName(), company.getRole());


        } else if (customerRepository.existsByEmail(email)) {
            Customer customer = customerRepository.findByEmail(email);
            return new UserDto(customer.getId(), customer.getEmail(), customer.getFirstName(), customer.getRole());


        } else if (adminRepository.existsByEmail(email)) {
            Admin admin = adminRepository.findByEmail(email);
            return new UserDto(admin.getId(), admin.getEmail(), admin.getRole());

        }
        return null;
    }
}
