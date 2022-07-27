package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.AdminDto;
import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.enums.Role;
import com.or.couponsproject.couponsproject.errors.Constraint;
import com.or.couponsproject.couponsproject.errors.exceptions.*;
import com.or.couponsproject.couponsproject.model.Admin;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.repo.AdminRepository;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.repo.CustomerRepository;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.repo.CompanyRepository;
import com.or.couponsproject.couponsproject.util.InputUserValidation;
import com.or.couponsproject.couponsproject.util.OptionalToEntityConvertorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    //------------------------------------------Creating company---------------------------------------------------

    public Company createCompany(final CompanyDto companyDto) throws ApplicationException {

        //Checking if hashed password is valid according to password REGEX
        //Checking if email address is valid according to Email REGEX
        if (!InputUserValidation.isPasswordValid(String.valueOf(companyDto.getPassword()))
                || !InputUserValidation.isEmailValid(companyDto.getEmail())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if company is already exists according to email
        //Checking if company is already exists according to name
        if (companyRepository.existsByEmail(companyDto.getEmail())
                || companyRepository.findByName(companyDto.getName()) != null) {
            throw new EntityExistException(EntityType.COMPANY, Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Inserting to Company its Role once it's creating
        companyDto.setRole(Role.COMPANY_ROLE);

        //Converting companyDto to spring company entity
        //Creating the company entity
        Company newCompany = companyRepository.save(ObjectMappingUtil.companyDtoToCompany(companyDto));
        log.info("The new company: " + newCompany.getName() + " has been created successfully!");

        return newCompany;
    }

    //------------------------------------------Updating company---------------------------------------------------

    public void updateCompany(final CompanyDto companyDto) throws ApplicationException {

        //Checking if user didn't enter id - Incorrect Update format
        if (companyDto.getId() == null) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if company is not exists
        if (!companyRepository.existsById(companyDto.getId())) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Extracting the specific company's name from our database
        String companyName = OptionalToEntityConvertorUtil.
                optionalCompany(companyRepository.findById(companyDto.getId())).getName();

        //Checking if company name has not entered and if not, setting the name from the existing one
        if (companyDto.getName() != null) {
            //Checking if company's name is already exist in database
            if (!companyDto.getName().equals(companyName)) {
                throw new CompanyNameException(Constraint.ENTITY_NAME_EXISTS);
            }
        } else {
            companyDto.setName(companyName);
        }

        //Setting a role to company
        companyDto.setRole(Role.COMPANY_ROLE);

        //Checking if password hasn't entered and setting it to a company
        if (companyDto.getPassword() == null) {
            companyDto.setPassword(String.valueOf(getCompany(companyDto.getId()).getPassword()));
            Company company = ObjectMappingUtil.companyDtoToCompanyUpdateWhenNoPass(companyDto);
            //Updating the company
            companyRepository.save(company);
            log.info("Company: " + company.getName() + " has been updated successfully!");
        } else {
            //Converting the companyDto object to spring company entity
            Company company = ObjectMappingUtil.companyDtoToCompanyUpdate(companyDto);

            //Updating the company
            companyRepository.save(company);
            log.info("Company: " + company.getName() + " has been updated successfully!");
        }
    }
    //------------------------------------------Deleting a company---------------------------------------------------

    public void deleteCompany(final Long companyId) throws ApplicationException {

        //Checking if company is not exists
        if (!companyRepository.existsById(companyId)) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting company name to a string variable
        String companyNameToDelete = OptionalToEntityConvertorUtil.
                optionalCompany(companyRepository.
                findById(companyId)).getName();

        //Deleting the specific company
        log.info("The selected company: " + companyNameToDelete + " has been deleted!");
        companyRepository.deleteById(companyId);
    }

    //------------------------------------------Getting all companies----------------------------------------------

    public List<CompanyDto> getAllCompanies() throws ApplicationException {

        //Setting and adding all companies in a list of dto companies
        List<CompanyDto> dtoCompanies = ObjectMappingUtil.companiesToCompaniesDto(companyRepository.findAll());

        //Checking if companies are not exist
        if (dtoCompanies.isEmpty()) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_LIST_NOT_EXISTS);
        }

        for (CompanyDto companyDto : dtoCompanies) {
            //Setting to every company its coupons
            companyDto.setCoupons(getCompany(companyDto.getId()).getCoupons());
        }
        return dtoCompanies;

    }
    //------------------------------------------Getting a company--------------------------------------------------

    public CompanyDto getCompany(final Long companyId) throws ApplicationException {

        //Checking if the company is not exist
        if(!companyRepository.existsById(companyId)){
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting a specific company to a variable
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Setting the company coupons list from our database
        company.setCoupons(couponRepository.getCouponsByCompanyId(companyId));

        List<CouponDto> dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(company.getCoupons());

        //Converting company to companyDto entity
        CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(company);
        //Setting the converted coupons company coupons list
        companyDto.setCoupons(dtoCoupons);

        return companyDto;
    }

    //------------------------------------------Creating a customer--------------------------------------------------

    public Customer createCustomer(final CustomerDto customerDto) throws ApplicationException {

        //Checking if hashed password address is valid according to password REGEX
        //Checking if email address is valid according to Email REGEX
        if (!InputUserValidation.isPasswordValid(customerDto.getPassword())
                || !InputUserValidation.isEmailValid(customerDto.getEmail())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if email of exists customer is already in use
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new EmailAlreadyInUse(Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Inserting to Customer his Role once it's creating
        customerDto.setRole(Role.CUSTOMER_ROLE);

        //Converting customerDto to spring customer entity
        //Creating the customer entity
        Customer newCustomer = customerRepository.save(ObjectMappingUtil.customerDtoToCustomer(customerDto));
        log.info("The new customer: " + newCustomer.getFirstName() + " " + newCustomer.getLastName() +
                " has been created successfully!");

        return newCustomer;
    }

    //------------------------------------------Updating a customer--------------------------------------------------

    public void updateCustomer(final CustomerDto customerDto) throws ApplicationException {

        //Checking if user didn't enter id - Incorrect Update format
        if (customerDto.getId() == null) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if customer is not exist
        if (!customerRepository.existsById(customerDto.getId())) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Getting a customer from the database and setting him in a variable
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerDto.getId()));

        //Checking if first name hasn't entered and setting it to a customer
        if (customerDto.getFirstName() == null) {
            customerDto.setFirstName(customer.getFirstName());
        }

        //Checking if last name hasn't entered and setting it to a customer
        if (customerDto.getLastName() == null) {
            customerDto.setLastName(customer.getLastName());
        }

        //Inserting to Customer his Role
        customerDto.setRole(Role.CUSTOMER_ROLE);

        //Checking if password hasn't entered and setting it to a customer
        if (customerDto.getPassword() == null) {
            customerDto.setPassword(String.valueOf(customer.getPassword()));
            Customer newCustomer = ObjectMappingUtil.customerDtoToCustomerUpdateWhenNoPass(customerDto);

            //Updating the customer
            customerRepository.save(newCustomer);
            log.info("The customer, " + newCustomer.getFirstName() + " has been updated successfully!");
        } else {
            //Converting the customerDto object to spring customer entity
            Customer newCustomer = ObjectMappingUtil.customerDtoToCustomerUpdate(customerDto);

            //Updating the customer
            customerRepository.save(newCustomer);
            log.info("The customer, " + newCustomer.getFirstName() + " has been updated successfully!");
        }
    }

    //------------------------------------------Deleting a customer--------------------------------------------------

    public void deleteCustomer(final Long customerId) throws ApplicationException {

        //Checking if customer is not exist
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting a specific customer object
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        log.info("The selected customer: " + customer.getFirstName() + " " +
                customer.getLastName() + " has been deleted!");
        //Deleting the specific customer
        customerRepository.deleteById(customerId);
    }

    //------------------------------------------Getting all customers-----------------------------------------------

        public List<CustomerDto> getAllCustomers() throws ApplicationException {

        //Setting and adding all companies in a list of dto customers
        List<CustomerDto> dtoCustomers = ObjectMappingUtil.customersToCustomersDto(customerRepository.findAll());

        //Checking if customers are not exist
        if (dtoCustomers.isEmpty()) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_LIST_NOT_EXISTS);
        }
        return dtoCustomers;
    }

    //------------------------------------------Getting a customer-------------------------------------------------

    public CustomerDto getCustomer(final Long customerId) throws ApplicationException {

        //Checking if the customer is not exist
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }
        //Setting a specific customer to a variable
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Converting customer to customerDto entity

        return ObjectMappingUtil.customerToCustomerDto(customer);
    }

    public AdminDto createAdmin(final AdminDto adminDto) throws ApplicationException {

        //Checking if hashed password address is valid according to password REGEX
        //Checking if email address is valid according to Email REGEX
        if (!InputUserValidation.isPasswordValid(adminDto.getPassword())
                || !InputUserValidation.isEmailValid(adminDto.getEmail())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if email of exists admin is already in use
        if (customerRepository.existsByEmail(adminDto.getEmail())) {
            throw new EmailAlreadyInUse(Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Inserting to Admin his Role once it's creating
        adminDto.setRole(Role.ADMIN_ROLE);

        //Converting and creating adminDto to spring admin entity
        adminRepository.save(ObjectMappingUtil.adminDtoToEntity(adminDto));
        log.info("The admin has been created successfully!");

         return adminDto;
    }
}

