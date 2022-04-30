package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.errors.Constraint;
import com.or.couponsproject.couponsproject.errors.exceptions.*;
import com.or.couponsproject.couponsproject.model.Customer;
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

    //------------------------------------------Creating company---------------------------------------------------

    public Company createCompany(final CompanyDto companyDto) throws ApplicationException {

        //Checking if hashed password is valid according to password REGEX
        if (!InputUserValidation.isPasswordValid(String.valueOf(companyDto.getPassword()))) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if email address is valid according to Email REGEX
        if (!InputUserValidation.isEmailValid(companyDto.getEmail())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if company is already exists according to email
        if (companyRepository.existsByEmail(companyDto.getEmail())) {
            throw new EntityExistException(EntityType.COMPANY, Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Checking if company is already exists according to name
        if (companyRepository.findByName(companyDto.getName()) != null) {
            throw new EntityExistException(EntityType.COMPANY, Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Converting companyDto to spring company entity
        Company company = ObjectMappingUtil.companyDtoToCompany(companyDto);

        //Creating the company entity
        Company newCompany = companyRepository.save(company);
        log.info("The new company: " + company.getName() + " has been created successfully!");

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

        //Checking if company's name is already exist in database
        if (!companyDto.getName().equals(companyName)) {
            throw new CompanyNameException(Constraint.ENTITY_NAME_EXISTS);
        }

        //Converting the companyDto object to spring company entity
        Company company = ObjectMappingUtil.companyDtoToCompanyUpdate(companyDto);

        //Updating the company
        companyRepository.save(company);
        log.info("Company: " + company.getName() + " has been updated and created successfully!");
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

    public Set<CompanyDto> getAllCompanies() throws ApplicationException {

        //Setting and adding all companies in a list of companies
        List<Company> companyList = new ArrayList<>(companyRepository.findAll());

        Set<CompanyDto> dtoCompanies = new HashSet<>();

        //Checking if companies are not exist
        if (companyList == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Transforming all companies to dto object and adding it to a set
        for (Company c : companyList) {
            CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(c);
            dtoCompanies.add(companyDto);
        }

        //Checking if dto companies are not exist
        if (dtoCompanies == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }
        for (CompanyDto companyDto : dtoCompanies) {
            //Setting to every customer its coupons
            companyDto.setCoupons(getCompany(companyDto.getId()).getCoupons());
        }
        return dtoCompanies;
    }
    //------------------------------------------Getting a company--------------------------------------------------

    public CompanyDto getCompany(final Long companyId) throws ApplicationException {

        //Setting a specific company to a variable
        Company company = OptionalToEntityConvertorUtil.optionalCompany(companyRepository.findById(companyId));

        //Checking if the company is not exist
        if (company == null) {
            throw new EntityNotExistException(EntityType.COMPANY, Constraint.ENTITY_NOT_EXISTS);
        }

        //Setting the company coupons list from our database
        company.setCoupons(couponRepository.getCouponsByCompanyId(companyId));

        List<CouponDto> dtoCoupons;

        dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(company.getCoupons());

        //Converting company to companyDto entity
        CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(company);
        //Setting the converted coupons company coupons list
        companyDto.setCoupons(dtoCoupons);

        return companyDto;
    }

    //------------------------------------------Creating a customer--------------------------------------------------

    public Customer createCustomer(final CustomerDto customerDto) throws ApplicationException {

        //Checking if hashed password address is valid according to password REGEX
        if (!InputUserValidation.isPasswordValid(customerDto.getPassword())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if email address is valid according to Email REGEX
        if (!InputUserValidation.isEmailValid(customerDto.getEmail())) {
            throw new UserValidationException(Constraint.INVALID_INPUT_FORMAT);
        }

        //Checking if email of exists customer is already in use
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new EmailAlreadyInUse(Constraint.ENTITY_ALREADY_EXISTS);
        }

        //Converting customerDto to spring customer entity
        Customer customer = ObjectMappingUtil.customerDtoToCustomer(customerDto);

        //Creating the customer entity
        Customer newCustomer = customerRepository.save(customer);
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

        //Converting the customerDto object to spring customer entity
        Customer customer = ObjectMappingUtil.customerDtoToCustomerUpdate(customerDto);

        //Updating the specific customer
        customerRepository.save(customer);

        log.info("The customer has been updated and created successfully!");
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

    public Set<CustomerDto> getAllCustomers() throws ApplicationException {

        //Setting and adding all companies in a list of companies
        List<Customer> customerList = new ArrayList<>(customerRepository.findAll());

        Set<CustomerDto> dtoCustomers = new HashSet<>();

        //Transforming all customers to dto object and adding it to a set
        for (Customer c : customerList) {
            CustomerDto customerDto = ObjectMappingUtil.customerToCustomerDto(c);
            dtoCustomers.add(customerDto);
        }

        //Checking if customers are not exist
        if (dtoCustomers == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        for (CustomerDto customer : dtoCustomers) {
            //Setting to every customer its coupons
            customer.setCoupons(getCustomer(customer.getId()).getCoupons());
        }
        return dtoCustomers;
    }

    //------------------------------------------Getting a customer-------------------------------------------------

    public CustomerDto getCustomer(final Long customerId) throws ApplicationException {

        //Setting a specific customer to a variable
        Customer customer = OptionalToEntityConvertorUtil.optionalCustomer(customerRepository.findById(customerId));

        //Checking if the customer is not exist
        if (customer == null) {
            throw new EntityNotExistException(EntityType.CUSTOMER, Constraint.ENTITY_NOT_EXISTS);
        }

        List<CouponDto> dtoCoupons;

        dtoCoupons = ObjectMappingUtil.couponsToCouponsDto(customer.getCoupons());

        //Converting customer to customerDto entity
        CustomerDto customerDto = ObjectMappingUtil.customerToCustomerDto(customer);
        //Setting the converted coupons to customer coupons list
        customerDto.setCoupons(dtoCoupons);

        return customerDto;
    }
}

