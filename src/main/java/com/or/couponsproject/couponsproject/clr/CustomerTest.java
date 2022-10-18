package com.or.couponsproject.couponsproject.clr;

import com.or.couponsproject.couponsproject.controllers.AuthController;
import com.or.couponsproject.couponsproject.dto.*;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Customer;
import com.or.couponsproject.couponsproject.repo.CustomerRepository;
import com.or.couponsproject.couponsproject.util.JwtHeaderPlacementUtil;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomerTest {

    private final RestTemplate restTemplate;
    private final AuthController authController;
    private final CustomerRepository customerRepository;
    private JwtDto jwt;

    //----------------------------------- Customer Test --> ------------------------------------------------------

    public boolean customerTest() throws ApplicationException {

        try {

            System.err.println("Customer logging ---> ");
            customerLogin("shaul@gmail.com", "shaul88");
            System.err.println("Coupon purchase ---> " + couponPurchase());
            System.err.println("Getting all customer coupons ---> " + getAllCustomerCoupons());
            System.err.println("Getting a customer ---> " + getCustomer());
            System.err.println("Getting coupons by a category ---> " + getCouponsByCategory());
            System.err.println("Getting coupons by max price ---> " + getCustomerCouponsByPrice());

        } catch (Exception e) {
            System.err.println("Customer Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.err.println(TestColorsConstants.ANSI_CYAN + "Customer Test has been passed successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Customer Register Test --> -----------------------------------------------

    public boolean customerRegister(CustomerDto customerDto) throws ApplicationException {

        try {

            //Setting the jwt token with customer credentials
            jwt = authController.registerCustomer(customerDto);

        } catch (Exception e) {
            System.err.println("Customer Registration Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.out.println(TestColorsConstants.ANSI_ORANGE + "The customer : " + customerDto.getFirstName() + " " +
                customerDto.getLastName() +
                "," + " Has been registered and logged in successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Customer Login Test --> -----------------------------------------------

    public boolean customerLogin(String email, String password) throws ApplicationException {

        //Getting a customer from database
        Customer customer = customerRepository.findByEmail(email);

        //Mapping a customer object to dto object
        CustomerDto customerDto = ObjectMappingUtil.customerToCustomerDto(customer);

        //Setting the dto customer password to the inserted one
        customerDto.setPassword(password);

        try {

            //Setting the jwt token with company credentials
            jwt = authController.login(customerDto);

            //Building a response entity of customer and inserting the token to the request header and setting a body
            ResponseEntity<UserDto> newCustomer = JwtHeaderPlacementUtil.
                    setHeaderToPostOrUpdate(jwt, customerDto);

            restTemplate.postForEntity(TestUrlConstants.LOGIN_USER, newCustomer, Void.class);

        } catch (Exception e) {
            System.err.println("Customer Login Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.out.println(TestColorsConstants.ANSI_ORANGE + "The customer : " + customerDto.getFirstName() + " " +
                customerDto.getLastName() + "," +
                " Has been logged in successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Coupon purchase by customer ------------------------------------------

    public boolean couponPurchase() throws ApplicationException {

        try {

            //Setting a post for entity of specific customer and coupon and activating the controller purchase method
            restTemplate.postForEntity("http://localhost:8080/customer/2/2",
                    //Inserting the token to the request header
                    JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                    null, Void.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Coupon purchase Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all customer coupon  -----------------------------------------

    public boolean getAllCustomerCoupons() throws ApplicationException {

        try {
            //Setting a response entity of coupons list dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange(TestUrlConstants.GET_ALL_CUSTOMER_COUPONS_URL + 2,
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CouponListDtoWrapper.class);

            //Getting a Coupons list body
            CouponListDtoWrapper couponsList = coupons.getBody();
            System.err.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting all customer Coupons Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting a customer -----------------------------------------------

    public boolean getCustomer() throws ApplicationException {

        try {
            //Setting a response entity of customer dto and activating the controller get method
            ResponseEntity<CustomerDto> customer = restTemplate.
                    exchange(TestUrlConstants.GET_CUSTOMER_URL + "3",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CustomerDto.class);

            //Getting a Customer body
            CustomerDto newCustomer = customer.getBody();
            System.err.println(newCustomer);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting a customer Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all customer coupon by category ---------------------------------

    public boolean getCouponsByCategory() throws ApplicationException {

        try {
            //Setting a response entity of coupons list dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange("http://localhost:8080/customer/coupons-by-category/2?category=FASHION",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CouponListDtoWrapper.class);

            //Getting a Coupons list body
            CouponListDtoWrapper couponsList = coupons.getBody();
            System.err.println(couponsList);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting coupons by category Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all customer coupon by price ---------------------------------

    public boolean getCustomerCouponsByPrice() throws ApplicationException {

        try {
            //Setting a response entity of coupons dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange("http://localhost:8080/customer/get-by-price/2?maxPrice=100.0",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CouponListDtoWrapper.class);

            //Setting a coupons list to a variable from the response entity
            CouponListDtoWrapper companyCouponsByPrice = coupons.getBody();
            System.err.println(companyCouponsByPrice);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting coupons by max price Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }
}
