package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CompanyListDtoWrapper;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.dto.CustomerListDtoWrapper;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/company")
    public Company createCompany(@RequestBody final CompanyDto company) throws ApplicationException {
        return adminService.createCompany(company);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/company")
    public void updateCompany(@RequestBody final CompanyDto company) throws ApplicationException {
        adminService.updateCompany(company);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/company/{companyId}")
    public void deleteCompany(@PathVariable final long companyId) throws ApplicationException {
        adminService.deleteCompany(companyId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/company")
    public CompanyListDtoWrapper getAllCompanies() throws ApplicationException {
        return new CompanyListDtoWrapper(adminService.getAllCompanies());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/company/{companyId}")
    public CompanyDto getCompany(@PathVariable final long companyId) throws ApplicationException {
        return adminService.getCompany(companyId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customer")
    public Customer createCustomer(@RequestBody final CustomerDto customer) throws ApplicationException {
        return adminService.createCustomer(customer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/customer")
    public void updateCustomer(@RequestBody final CustomerDto customer) throws ApplicationException {
        adminService.updateCustomer(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/customer/{customerId}")
    public void deleteCustomer(@PathVariable final long customerId) throws ApplicationException {
        adminService.deleteCustomer(customerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer")
    public CustomerListDtoWrapper getAllCustomers() throws ApplicationException {
        return new CustomerListDtoWrapper(adminService.getAllCustomers());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/customer/{customerId}")
    public CustomerDto getCustomer(@PathVariable final long customerId) throws ApplicationException {
        return adminService.getCustomer(customerId);
    }
}
