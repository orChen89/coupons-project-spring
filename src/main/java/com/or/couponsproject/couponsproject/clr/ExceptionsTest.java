package com.or.couponsproject.couponsproject.clr;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.AdminService;
import com.or.couponsproject.couponsproject.service.CompanyService;
import com.or.couponsproject.couponsproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExceptionsTest {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;

    //----------------------------------- Exceptions Tests --> ---------------------------------------------------

    public boolean advancedTests() throws ApplicationException {

        System.out.println();
        System.out.println(TestColorsConstants.ANSI_PINK_BACKGROUND +
                TestColorsConstants.ANSI_BLACK +
                "Entering to Exception Tests -->" +
                TestColorsConstants.ANSI_DEFAULT_RESET);

        try {

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of company with same existing company's email -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Company already exist
                adminService.createCompany(CompanyDto.builder().
                        name("Sababa").
                        email("kal@gmail.com").
                        password("sabab012").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
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

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing updating an existing company's name -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Company's name is already exists
                adminService.updateCompany(CompanyDto.builder().
                        id(3L).
                        name("kalevet").
                        email("com2@gmail.com").
                        password("Com2").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of new customer with same email of an existing customer -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Email is already exists and in use
                adminService.createCustomer(CustomerDto.builder().
                        firstName("Dor").
                        lastName("Josh").
                        email("shaul@gmail.com").
                        password("asd123").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing purchase of same coupon -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Coupon already purchased
                customerService.addCouponPurchase(2L, 2L);

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing purchase of coupon that not in stock anymore -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Coupon is currently not in stock
                customerService.addCouponPurchase(3L, 4L);

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN +
                    "Testing creation of coupon with same title as of existing one related to same company -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {

                companyService.createCoupon(CouponDto.builder().
                        companyId(3L).
                        category(CouponCategory.FASHION).
                        title("Jeans").
                        description("Yafe & skinny").
                        startDate(LocalDate.of(2022, 2, 5)).
                        endDate(LocalDate.of(2022, 9, 16)).
                        amount(100).
                        price(55.0).
                        imageUrl("jdbc:mysql://localhost:3306/coupons_project/62").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of coupon with invalid end date -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Date is invalid
                companyService.createCoupon(CouponDto.builder().
                        companyId(3L).
                        category(CouponCategory.FOOD).
                        title("Toast-Naknik").
                        description("Taim").
                        startDate(LocalDate.of(2025, 1, 28)).
                        endDate(LocalDate.of(2022, 8, 15)).
                        amount(4).
                        price(32.5).
                        imageUrl("jdbc:mysql://localhost:3306/coupons_project/12").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            System.out.println();
            log.info(TestColorsConstants.ANSI_GREEN + "Testing creation of coupon with expired end date -->" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            try {
                //If test valid - we will receive an exception - Expiration Date Arrived
                companyService.createCoupon(CouponDto.builder().
                        companyId(3L).
                        category(CouponCategory.FASHION).
                        title("Belt").
                        description("Wow").
                        startDate(LocalDate.of(2022, 2, 23)).
                        endDate(LocalDate.of(2022, 4, 17)).
                        amount(9).
                        price(120.0).
                        imageUrl("jdbc:mysql://localhost:3306/coupons_project/13").
                        build());

            } catch (ApplicationException e) {
                log.error(TestColorsConstants.ANSI_RED + e.getMessage() + TestColorsConstants.ANSI_DEFAULT_RESET);
                log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                        TestColorsConstants.ANSI_DEFAULT_RESET);
            }

            //Catching all Exceptions
        } catch (Exception e) {
            System.err.println("Exceptions Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.err.println(TestColorsConstants.ANSI_ORANGE + "Exceptions Test has been passed successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }
}
