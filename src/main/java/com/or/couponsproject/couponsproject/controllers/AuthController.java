package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.dto.JwtDto;
import com.or.couponsproject.couponsproject.dto.UserDto;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final AdminService adminService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public JwtDto login(@RequestBody final UserDto user) throws ApplicationException {
        return authService.authenticate(user);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/register/company")
    public JwtDto registerCompany(@RequestBody final CompanyDto company) throws ApplicationException {
        adminService.createCompany(company);
        return authService.authenticate(company);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/register/customer")
    public JwtDto registerCustomer(@RequestBody final CustomerDto customer) throws ApplicationException {
        adminService.createCustomer(customer);
        return authService.authenticate(customer);
    }
}
