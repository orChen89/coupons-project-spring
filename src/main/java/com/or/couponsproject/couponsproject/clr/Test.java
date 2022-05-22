package com.or.couponsproject.couponsproject.clr;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.service.CompanyService;
import com.or.couponsproject.couponsproject.service.CustomerService;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class Test implements CommandLineRunner {

    private final RestTemplate restTemplate;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    @Override
    public void run(String... args) {

        log.info("Entering to Application Tests -->");

        entitiesAndPurchasesCreation();
        adminTest();
        companyTest();
        customerTest();
        advancedTests();
    }

    //-----------------------------------Companies creation by default -->-----------------------------------

    public void entitiesAndPurchasesCreation() {

        List<CompanyDto> companiesList = new ArrayList<>();

        companiesList.add(CompanyDto.builder().
                name("kalevet").
                email("kal@gmail.com").
                password("kalev565").
                build());

        companiesList.add(CompanyDto.builder().
                name("Orburger").
                email("com1@gmail.com").
                password("Com1").
                build());

        companiesList.add(CompanyDto.builder().
                name("Michnasaim").
                email("com2@gmail.com").
                password("Com2").
                build());

        companiesList.add(CompanyDto.builder().
                name("Hashmal").
                email("com3@gmail.com").
                password("Com3").
                build());

        companiesList.add(CompanyDto.builder().
                name("Hul").
                email("com4@gmail.com").
                password("Com4").
                build());

        companiesList.add(CompanyDto.builder().
                name("Shualim").
                email("com5@gmail.com").
                password("Com5").
                build());

        //Creating operation for each company
        companiesList.forEach(company -> {
            try {
                adminService.createCompany(company);
            } catch (ApplicationException e) {
                log.error(e.getMessage());
            }
        });

        //-----------------------------------Coupons creation by default -->-----------------------------------

        List<CouponDto> couponsList = new ArrayList<>();

        couponsList.add(CouponDto.builder().
                companyID(1L).
                category(CouponCategory.MEDICINE).
                title("Kalevet Vaccination").
                description("Work!").
                startDate(LocalDate.of(2022, 4, 5)).
                endDate(LocalDate.of(2022, 12, 5)).
                amount(50).
                price(50.0).
                image("jdbc:mysql://localhost:3306/coupons_project/1").
                build());

        couponsList.add(CouponDto.builder().
                companyID(3L).
                category(CouponCategory.FASHION).
                title("Jeans").
                description("pants").
                startDate(LocalDate.of(2022, 2, 28)).
                endDate(LocalDate.of(2022, 8, 10)).
                amount(1).
                price(78.5).
                image("jdbc:mysql://localhost:3306/coupons_project/2").
                build());

        couponsList.add(CouponDto.builder().
                companyID(2L).
                category(CouponCategory.FOOD).
                title("Lamburger").
                description("Til").
                startDate(LocalDate.of(2022, 2, 28)).
                endDate(LocalDate.of(2022, 8, 10)).
                amount(32).
                price(70.5).
                image("jdbc:mysql://localhost:3306/coupons_project/3").
                build());

        couponsList.add(CouponDto.builder().
                companyID(4L).
                category(CouponCategory.ELECTRICITY).
                title("WIFI AC").
                description("kal").
                startDate(LocalDate.of(2022, 3, 20)).
                endDate(LocalDate.of(2022, 11, 17)).
                amount(10).
                price(1600.0).
                image("jdbc:mysql://localhost:3306/coupons_project/4").
                build());

        couponsList.add(CouponDto.builder().
                companyID(5L).
                category(CouponCategory.VACATION).
                title("Tenerif").
                description("All included").
                startDate(LocalDate.of(2022, 6, 30)).
                endDate(LocalDate.of(2022, 8, 20)).
                amount(8).
                price(3500.0).
                image("jdbc:mysql://localhost:3306/coupons_project/5").
                build());

        couponsList.add(CouponDto.builder().
                companyID(4L).
                category(CouponCategory.ELECTRICITY).
                title("Oled LG TV").
                description("Metoraf").
                startDate(LocalDate.of(2022, 4, 4)).
                endDate(LocalDate.of(2022, 9, 29)).
                amount(22).
                price(4500.0).
                image("jdbc:mysql://localhost:3306/coupons_project/6").
                build());

        //Creating operation for each coupon
        couponsList.forEach(coupon -> {
            try {
                companyService.createCoupon(coupon);
            } catch (ApplicationException e) {
                log.error(e.getMessage());
            }
        });

        //-----------------------------------Customers creation by default -->-----------------------------------

        List<CustomerDto> customerList = new ArrayList<>();

        customerList.add(CustomerDto.builder().
                firstName("Or").
                lastName("Chen").
                email("Or@gmail.com").
                password("asd123").
                build());

        customerList.add(CustomerDto.builder().
                firstName("Shaul").
                lastName("Shalom").
                email("user1@gmail.com").
                password("User1").
                build());

        customerList.add(CustomerDto.builder().
                firstName("Kadosh").
                lastName("Rezach").
                email("user2@gmail.com").
                password("User2").
                build());

        customerList.add(CustomerDto.builder().
                firstName("Gever").
                lastName("Levy").
                email("user3@gmail.com").
                password("User3").
                build());

        customerList.add(CustomerDto.builder().
                firstName("Miki").
                lastName("Liki").
                email("user4@gmail.com").
                password("User4").
                build());

        //Creating operation for each customer
        customerList.forEach(customer -> {
            try {
                adminService.createCustomer(customer);
            } catch (ApplicationException e) {
                log.error(e.getMessage());
            }
        });

        //-----------------------------------Purchase creation by default -->-----------------------------------

        try {

            customerService.addCouponPurchase(1L, 1L);
            customerService.addCouponPurchase(2L, 1L);
            customerService.addCouponPurchase(3L, 3L);
            customerService.addCouponPurchase(4L, 2L);
            customerService.addCouponPurchase(5L, 6L);
            customerService.addCouponPurchase(5L, 4L);
            customerService.addCouponPurchase(2L, 6L);
            customerService.addCouponPurchase(1L, 4L);

        } catch (ApplicationException ae) {
            log.error(ae.getMessage());
        }
    }

    //----------------------------------- Admin test -->--------------------------------------------------

    public boolean adminTest() {

        try {

            System.out.println(companyCreation());
            System.out.println(companyUpdate());
            System.out.println(companyDeletion());
            System.out.println(getAllCompanies());
            System.out.println(getCompanyByAdmin());
            System.out.println(customerCreation());
            System.out.println(customerUpdate());
            System.out.println(customerDeletion());
            System.out.println(getAllCustomers());
            System.out.println(getCustomerByAdmin());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Company test -->--------------------------------------------------

    public boolean companyTest() {
        try {

            System.out.println(couponCreation());
            System.out.println(couponUpdate());
            System.out.println(couponDeletion());
            System.out.println(getCoupon());
            System.out.println(getAllCompanyCoupons());
            System.out.println(getCompanyCouponsByCategory());
            System.out.println(getCompanyCouponsByPrice());
            System.out.println(getCompany());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Customer test -->--------------------------------------------------

    public boolean customerTest() {
        try {
            System.out.println(purchaseCreation());
            System.out.println(getAllCustomerCoupons());
            System.out.println(getCustomer());
            System.out.println(getCustomerCouponsByCategory());
            System.out.println(getCustomerCouponsByPrice());

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return true;
    }

    //----------------------------------- ALL METHODS TESTS -->--------------------------------------------------

    public boolean companyCreation() {

        //Setting a company in a variable
        CompanyDto companyDto =
                CompanyDto.builder().
                        name("mor").
                        email("moro@gmail.com").
                        password("sha123").
                        build();

        try {
            //Setting a response entity of company and activating the controller post method
            ResponseEntity<CompanyDto> company = restTemplate.
                    postForEntity(TestUrlConstants.POST_OR_UPDATE_COMPANY_URL, companyDto, CompanyDto.class);

            //Checking the company body if not null
            CompanyDto companyResponse = company.getBody();
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return companyResponse != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean companyUpdate() {

        //Setting a company in a http request
        HttpEntity<CompanyDto> request = new HttpEntity<>(
                CompanyDto.builder().
                        id(7L).
                        name("mor").
                        email("morogol@gmail.com").
                        password("sha123").
                        build());

        try {
            //Activating the controller update method
            restTemplate.put(TestUrlConstants.POST_OR_UPDATE_COMPANY_URL, request, HttpMethod.PUT, Void.class);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean companyDeletion() {

        try {
            //Activating the controller delete method on url
            restTemplate.delete(TestUrlConstants.COMPANY_DELETION_URL + "1");
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getAllCompanies() {

        try {
            //Setting a response entity of company dto array and activating the controller get all method
            ResponseEntity<CompanyDto[]> companiesList = restTemplate.
                    getForEntity(TestUrlConstants.GETTING_ALL_COMPANIES_URL, CompanyDto[].class);

            //Getting a companies list to an objects array body
            CompanyDto[] companyDtos = companiesList.getBody();

            assert companyDtos != null;
            //Placing the received body of company set in a set variable
            Set<CompanyDto> companies = (Set.of(companyDtos));
            System.out.println(companies);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return companies != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCompanyByAdmin() {

        try {
            //Setting a response entity of company dto array and activating the controller get all method
            ResponseEntity<CompanyDto> companyDto = restTemplate.
                    getForEntity(TestUrlConstants.GET_COMPANY_BY_ADMIN_URL + "4", CompanyDto.class);

            //Getting a company body
            CompanyDto company = companyDto.getBody();
            System.out.println(company);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return company != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean customerCreation() {

        //Setting a customer in a variable
        CustomerDto customerDto = CustomerDto.builder().
                firstName("Tav").
                lastName("La").
                email("Tavos@gmail.com").
                password("Tav123").build();

        try {
            //Setting a response entity of customer and activating the controller post method
            ResponseEntity<CustomerDto> customer = restTemplate.
                    postForEntity(TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL, customerDto, CustomerDto.class);

            //Checking the customer body if not null
            CustomerDto customerResponse = customer.getBody();
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return customerResponse == null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean customerUpdate() {

        //Setting a customer in a http request
        HttpEntity<CustomerDto> request = new HttpEntity<>(
                CustomerDto.builder().
                        id(1L).
                        firstName("Or").
                        lastName("Chen").
                        email("oroosh@gmail.com").
                        password("Or123").
                        build());

        try {
            //Activating the controller update method
            restTemplate.put(TestUrlConstants.POST_OR_UPDATE_CUSTOMER_URL, request, HttpMethod.PUT, Void.class);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean customerDeletion() {

        try {
            //Activating the controller delete method on url
            restTemplate.delete(TestUrlConstants.CUSTOMER_DELETION_URL + "4");
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getAllCustomers() {

        try {
            //Setting a response entity of customer dto array and activating the controller get all method
            ResponseEntity<CustomerDto[]> customersList = restTemplate.
                    getForEntity(TestUrlConstants.GETTING_ALL_CUSTOMERS_URL, CustomerDto[].class);

            //Getting a customers list to an objects array body
            CustomerDto[] customreDtos = customersList.getBody();

            assert customreDtos != null;
            //Placing the received body of customers set in a set variable
            Set<CustomerDto> customers = (Set.of(customreDtos));
            System.out.println(customers);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return customers != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCustomerByAdmin() {

        try {
            //Setting a response entity of customer dto array and activating the controller get all method
            ResponseEntity<CustomerDto> customerDto = restTemplate.
                    getForEntity(TestUrlConstants.GET_CUSTOMER_BY_ADMIN_URL + "5", CustomerDto.class);

            //Getting a customer body
            CustomerDto customer = customerDto.getBody();
            System.out.println(customer);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return customer != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean couponCreation() {

        //Setting a coupon in a variable
        CouponDto couponDto =
                CouponDto.builder().
                        companyID(5L).
                        category(CouponCategory.VACATION).
                        title("London").
                        description("Big Ben").
                        startDate(LocalDate.of(2022, 3, 28)).
                        endDate(LocalDate.of(2022, 10, 7)).
                        amount(2).
                        price(1500.0).
                        image("jdbc:mysql://localhost:3306/coupons_project/10").
                        build();

        try {
            //Setting a response entity of coupon and activating the controller post method
            ResponseEntity<CouponDto> coupon = restTemplate.
                    postForEntity(TestUrlConstants.POST_OR_UPDATE_COUPON_URL, couponDto, CouponDto.class);

            //Checking the coupon body if not null
            CouponDto couponResponse = coupon.getBody();
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            System.out.println(couponResponse);
            return couponResponse != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean couponUpdate() {

        //Setting a coupon in a http request
        HttpEntity<CouponDto> request = new HttpEntity<>(
                CouponDto.builder().
                        id(6L).
                        companyID(4L).
                        category(CouponCategory.ELECTRICITY).
                        title("Oled LG TV").
                        description("Metoraf").
                        startDate(LocalDate.of(2022, 4, 4)).
                        endDate(LocalDate.of(2022, 9, 29)).
                        amount(10).
                        price(4500.0).
                        image("jdbc:mysql://localhost:3306/coupons_project/6").
                        build());

        try {
            //Activating the controller update method
            restTemplate.put(TestUrlConstants.POST_OR_UPDATE_COUPON_URL, request, HttpMethod.PUT, Void.class);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean couponDeletion() {

        try {
            //Activating the controller delete method on url
            restTemplate.delete(TestUrlConstants.COUPON_DELETION_URL + "6");
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCoupon() {

        try {
            //Setting a response entity of coupon dto nd activating the controller get method
            ResponseEntity<CouponDto> couponDto = restTemplate.
                    getForEntity(TestUrlConstants.GET_COUPON_URL + "3", CouponDto.class);

            //Getting a coupon body
            CouponDto coupon = couponDto.getBody();
            System.out.println(coupon);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return coupon != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getAllCompanyCoupons() {

        try {
            //Setting a response entity of coupons dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity(TestUrlConstants.GETTING_ALL_COMPANY_COUPONS_URL + "2", CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] companyCoupons = coupons.getBody();

            assert companyCoupons != null;
            //Placing the received body of coupons list in a set variable
            List<CouponDto> couponsList = (List.of(companyCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCompanyCouponsByCategory() {

        try {
            //Setting a response entity of coupons dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity("http://localhost:8080/company/getByCategory/5/VACATION",
                            CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] companyCoupons = coupons.getBody();

            assert companyCoupons != null;
            //Placing the received body of coupons list in a set variable
            List<CouponDto> couponsList = (List.of(companyCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCompanyCouponsByPrice() {

        try {
            //Setting a response entity of coupons dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity("http://localhost:8080/company/getByMaxPrice/4/5000.0",
                            CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] companyCoupons = coupons.getBody();

            assert companyCoupons != null;
            //Placing the received body of coupons list in a set variable
            List<CouponDto> couponsList = (List.of(companyCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCompany() {

        try {
            //Setting a response entity of company dto and activating the controller get method
            ResponseEntity<CompanyDto> companyDto = restTemplate.
                    getForEntity(TestUrlConstants.GET_COMPANY_URL + "2", CompanyDto.class);

            //Getting a company body
            CompanyDto company = companyDto.getBody();
            System.out.println(company);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return company != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean purchaseCreation() {

        try {
            //Setting a post for entity of specific customer and coupon and activating the controller purchase method
            restTemplate.postForEntity("http://localhost:8080/customer/purchase/1/4",
                    null, Void.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return true;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getAllCustomerCoupons() {

        try {
            //Setting a response entity of coupon dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity(TestUrlConstants.GET_ALL_CUSTOMER_COUPONS_URL + "3", CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] customerCoupons = coupons.getBody();

            assert customerCoupons != null;
            //Placing the received body of coupons set in a set variable
            List<CouponDto> couponsList = (List.of(customerCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCustomer() {

        try {
            //Setting a response entity of customer dto and activating the controller get method
            ResponseEntity<CustomerDto> customerDto = restTemplate.
                    getForEntity(TestUrlConstants.GET_CUSTOMER_URL + "3", CustomerDto.class);

            //Getting a customer body
            CustomerDto customer = customerDto.getBody();
            System.out.println(customer);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return customer != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCustomerCouponsByCategory() {

        try {
            //Setting a response entity of coupons dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity("http://localhost:8080/customer/getCouponsByCategory/1/ELECTRICITY",
                            CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] customerCoupons = coupons.getBody();

            assert customerCoupons != null;
            //Placing the received body of coupon list in a set variable
            List<CouponDto> couponsList = (List.of(customerCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean getCustomerCouponsByPrice() {

        try {
            //Setting a response entity of coupons dto array and activating the controller get all method
            ResponseEntity<CouponDto[]> coupons = restTemplate.
                    getForEntity("http://localhost:8080/customer/getCouponsByMaxPrice/3/100.0",
                            CouponDto[].class);

            //Getting a coupons list to an objects array body
            CouponDto[] customerCoupons = coupons.getBody();

            assert customerCoupons != null;
            //Placing the received body of coupon list in a set variable
            List<CouponDto> couponsList = (List.of(customerCoupons));
            System.out.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            return couponsList != null;
            //Catching all exceptions
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    //----------------------------------- Exceptions Tests -->---------------------------------------------------

    private void advancedTests() {

        log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of company with same existing company's email -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Company already exist
            adminService.createCompany(CompanyDto.builder().
                    name("Sababa").
                    email("com3@gmail.com").
                    password("sabab012").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing getting a company that is not exist -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Company is not exist
            adminService.getCompany(9L);

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing updating an existing company's name -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Company's name is already exists
            adminService.updateCompany(CompanyDto.builder().
                    id(3L).
                    name("Wallak").
                    email("com2@gmail.com").
                    password("Com2").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of new customer with same email of an existing customer -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Email is already exists and in use
            adminService.createCustomer(CustomerDto.builder().
                    firstName("Dor").
                    lastName("Josh").
                    email("oroosh@gmail.com").
                    password("asd123").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing purchase of same coupon -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Coupon already purchased
            customerService.addCouponPurchase(5L, 4L);

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing purchase of coupon that not in stock anymore -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Coupon is currently not in stock
            customerService.addCouponPurchase(1L, 2L);

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN +
                "Testing creation of coupon with same title as of existing one related to same company -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {

            companyService.createCoupon(CouponDto.builder().
                    companyID(7L).
                    category(CouponCategory.MEDICINE).
                    title("Kalevet Vaccination").
                    description("Dog Protector").
                    startDate(LocalDate.of(2022, 2, 5)).
                    endDate(LocalDate.of(2022, 9, 16)).
                    amount(100).
                    price(55.0).
                    image("jdbc:mysql://localhost:3306/coupons_project/9").
                    build());

            //If test valid - we will receive an exception - Coupon already exist
            companyService.createCoupon(CouponDto.builder().
                    companyID(7L).
                    category(CouponCategory.MEDICINE).
                    title("Kalevet Vaccination").
                    description("NEW!!").
                    startDate(LocalDate.of(2022, 4, 5)).
                    endDate(LocalDate.of(2022, 12, 8)).
                    amount(300).
                    price(70.0).
                    image("jdbc:mysql://localhost:3306/coupons_project/15").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of coupon with invalid end date -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Date is invalid
            companyService.createCoupon(CouponDto.builder().
                    companyID(2L).
                    category(CouponCategory.FOOD).
                    title("Toast-Naknik").
                    description("Taim").
                    startDate(LocalDate.of(2025, 1, 28)).
                    endDate(LocalDate.of(2022, 8, 15)).
                    amount(4).
                    price(32.5).
                    image("jdbc:mysql://localhost:3306/coupons_project/12").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }

        log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of coupon with expired end date -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        try {
            //If test valid - we will receive an exception - Expiration Date Arrived
            companyService.createCoupon(CouponDto.builder().
                    companyID(3L).
                    category(CouponCategory.FASHION).
                    title("Belt").
                    description("Wow").
                    startDate(LocalDate.of(2022, 2, 23)).
                    endDate(LocalDate.of(2022, 4, 17)).
                    amount(9).
                    price(120.0).
                    image("jdbc:mysql://localhost:3306/coupons_project/13").
                    build());

        } catch (ApplicationException e) {
            log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
        }
    }
}




