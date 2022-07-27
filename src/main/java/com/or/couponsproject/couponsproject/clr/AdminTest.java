package com.or.couponsproject.couponsproject.clr;

import com.or.couponsproject.couponsproject.controllers.AuthController;
import com.or.couponsproject.couponsproject.dto.*;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.util.JwtHeaderPlacementUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdminTest {

    private final RestTemplate restTemplate;
    private final AdminService adminService;
    private final AuthController authController;
    private JwtDto jwt;

    //----------------------------------- Testing Admin --> --------------------------------------------------

    public boolean adminTest() throws ApplicationException {

        try {

            System.err.println("Admin logging ---> " + adminLogin());
            System.err.println("Creating companies ---> " + createCompaniesByAdmin());
            System.err.println("Getting a Company ---> " + getCompanyByAdmin());
            System.err.println("Updating a Company ---> " + updateCompanyByAdmin());
            System.err.println("Deleting a Company ---> " + deleteCompanyByAdmin());
            System.err.println("Getting all companies ---> " + getAllCompaniesByAdmin());
            System.err.println("Creating Customers ---> " + createCustomersByAdmin());
            System.err.println("Updating a Customer ---> " + updateCustomerByAdmin());
            System.err.println("Getting a Customer ---> " + getCustomerByAdmin());
            System.err.println("Deleting a Customer ---> " + deleteCustomerByAdmin());
            System.err.println("Getting all customers ---> " + getAllCustomersByAdmin());

        } catch (Exception e) {
            System.err.println("Admin Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.err.println(TestColorsConstants.ANSI_CYAN + "Admin Test has been passed successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Admin Login --> --------------------------------------------------

    public boolean adminLogin() throws ApplicationException {

        try {

            AdminDto admin = adminService.createAdmin(AdminDto.builder().
                    email("admin@admin.com").
                    password("admin").
                    build());

            //Setting the jwt token with admin credentials
            jwt = authController.login(admin);

            //Building a response entity of admin and inserting the token to the request header and setting a body
            ResponseEntity<UserDto> newAdmin = JwtHeaderPlacementUtil.
                    setHeaderToPostOrUpdate(jwt, admin);

            restTemplate.postForEntity(TestUrlConstants.LOGIN_USER, newAdmin, UserDto.class);

        } catch (Exception e) {
            System.err.println("Admin Login Test: " + false);
            throw new ApplicationException(e.getMessage());

        }
        System.out.println(TestColorsConstants.ANSI_ORANGE + "Admin Has been logged in successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Companies creation By Admin --> ------------------------------------------

    public boolean createCompaniesByAdmin() throws ApplicationException {

        CompanyDto companyDto1 = CompanyDto.builder().name("Daniel").email("dani@gmail.com").password("1234").build();
        CompanyDto companyDto2 = CompanyDto.builder().name("kalevet").email("kal@gmail.com").password("kalev565").build();
        CompanyDto companyDto3 = CompanyDto.builder().name("Orburger").email("com1@gmail.com").password("Com1").build();

        //Building a response entity of company and inserting the token to the request header and setting a body
        ResponseEntity<UserDto> newCompany1 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, companyDto1);
        ResponseEntity<UserDto> newCompany2 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, companyDto2);
        ResponseEntity<UserDto> newCompany3 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, companyDto3);

        try {

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COMPANY_BY_ADMIN_URL,
                            newCompany1,
                            CompanyDto.class);

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COMPANY_BY_ADMIN_URL,
                            newCompany2,
                            CompanyDto.class);

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COMPANY_BY_ADMIN_URL,
                            newCompany3,
                            CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Creating Companies By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Updating a Company By Admin --> ------------------------------------------

    public boolean updateCompanyByAdmin() throws ApplicationException {

        CompanyDto companyDto = CompanyDto.builder().
                id(1L).
                email("dandan@gmail.com").
                build();

        //Building a response entity of company and inserting the token to the request header and setting a body
        ResponseEntity<UserDto> newCompany = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, companyDto);

        try {
            restTemplate.exchange(
                    TestUrlConstants.POST_OR_UPDATE_COMPANY_BY_ADMIN_URL,
                    HttpMethod.PUT,
                    newCompany,
                    CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Updating a Company By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting a company By Admin --> ------------------------------------------

    public boolean getCompanyByAdmin() throws Exception {

        CompanyDto company;

        try {

            //Setting a response entity of company dto array and activating the controller get method
            ResponseEntity<CompanyDto> newCompany = restTemplate.
                    exchange(TestUrlConstants.GET_COMPANY_BY_ADMIN_URL + "1",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CompanyDto.class);

            //Getting a company body
            company = newCompany.getBody();
            System.err.println(company);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting a Company By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Company deletion By Admin --> ------------------------------------------

    public boolean deleteCompanyByAdmin() throws ApplicationException {

        try {
            //Activating the controller delete method
            restTemplate.exchange(TestUrlConstants.COMPANY_DELETION_URL + "1",
                    HttpMethod.DELETE,
                    //Inserting the token to the request header
                    JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                    CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Deleting Company By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all Companies By Admin --> ------------------------------------------

    public boolean getAllCompaniesByAdmin() throws ApplicationException {

        CompanyListDtoWrapper companyList;

        try {

            //Setting a response entity of company dto array and activating the controller get all method
            ResponseEntity<CompanyListDtoWrapper> companies = restTemplate.
                    exchange(TestUrlConstants.GETTING_ALL_COMPANIES_URL,
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CompanyListDtoWrapper.class);

            //Getting a company body
            companyList = companies.getBody();
            System.err.println(companyList);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

          //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting all Companies By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Customers creation By Admin --> ------------------------------------------

    public boolean createCustomersByAdmin() throws ApplicationException {

        CustomerDto customerDto1 = CustomerDto.builder().
                firstName("Or").
                lastName("Chen").
                email("Or@gmail.com").
                password("asd123").
                build();

        CustomerDto customerDto2 = CustomerDto.builder().
                firstName("Shaul").
                lastName("Shalom").
                email("shaul@gmail.com").
                password("shaul88").
                build();

        CustomerDto customerDto3 = CustomerDto.builder().
                firstName("Tomer").
                lastName("Mor").
                email("tomerm@gmail.com").
                password("tomer123").
                build();

        //Building a response entity of customer and inserting the token to the request header and setting a body
        ResponseEntity<UserDto> newCustomer1 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, customerDto1);
        ResponseEntity<UserDto> newCustomer2 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, customerDto2);
        ResponseEntity<UserDto> newCustomer3 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, customerDto3);

        try {
                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL,
                            newCustomer1,
                            CustomerDto.class);

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL,
                            newCustomer2,
                            CustomerDto.class);

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL,
                            newCustomer3,
                            CustomerDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Create Customer By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Updating a customer By Admin --> ------------------------------------------

    public boolean updateCustomerByAdmin() throws ApplicationException {

        CustomerDto customerDto = CustomerDto.builder().
                id(3L).
                email("tomi@gmail.com").
                password("5555").
                build();

        //Building a response entity of customer and inserting the token to the request header and setting a body
        ResponseEntity<UserDto> newCustomer = JwtHeaderPlacementUtil.setHeaderToPostOrUpdate(jwt, customerDto);

        try {
            restTemplate.exchange(
                    TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL,
                    HttpMethod.PUT,
                    newCustomer,
                    CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Updating Customer By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting a customer By Admin --> ------------------------------------------

    public boolean getCustomerByAdmin() throws ApplicationException {

        try {

            //Setting a response entity of customer dto array and activating the controller get method
            ResponseEntity<CustomerDto> customerDto = restTemplate.
                    exchange(TestUrlConstants.GET_CUSTOMER_BY_ADMIN_URL + "2",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CustomerDto.class);

            //Getting a customer body
            CustomerDto newCustomer = customerDto.getBody();
            System.err.println(newCustomer);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Get Customer By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Deletion of customer By Admin --> ------------------------------------------

    public boolean deleteCustomerByAdmin() throws ApplicationException {

        try {
            restTemplate.exchange(TestUrlConstants.CUSTOMER_DELETION_URL + "1",
                    HttpMethod.DELETE,
                    //Inserting the token to the request header
                    JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                    CustomerDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Delete Customer By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all customers By Admin --> ------------------------------------------

    public boolean getAllCustomersByAdmin() throws ApplicationException {

        try {

            //Setting a response entity of companies list dto and activating the controller get all method
            ResponseEntity<CustomerListDtoWrapper> customers = restTemplate.
                    exchange(TestUrlConstants.GETTING_ALL_CUSTOMERS_URL,
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CustomerListDtoWrapper.class);

            //Getting a Customers list body
            CustomerListDtoWrapper customersList = customers.getBody();
            System.err.println(customersList);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Get All Customers By Admin : " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }
}


