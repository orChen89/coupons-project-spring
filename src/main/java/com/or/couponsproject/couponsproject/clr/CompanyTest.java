package com.or.couponsproject.couponsproject.clr;
import com.or.couponsproject.couponsproject.controllers.AuthController;
import com.or.couponsproject.couponsproject.dto.*;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.repo.CompanyRepository;
import com.or.couponsproject.couponsproject.util.JwtHeaderPlacementUtil;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompanyTest {

    private final RestTemplate restTemplate;
    private final AuthController authController;
    private final CompanyRepository companyRepository;
    private JwtDto jwt;

    //----------------------------------- Company Test --> ------------------------------------------------------

    public boolean companyTest() throws ApplicationException {

        try {

            System.err.println("Company logging ---> ");
            companyLogin("kal@gmail.com", "kalev565");
            System.err.println("Creating coupons ---> " + createCoupons());
            System.err.println("Updating coupons ---> " + updateCoupon());
            System.err.println("Deleting a coupon ---> " + deleteCoupon());
            System.err.println("Getting a coupon ---> " + getCoupon());
            System.err.println("Getting all coupons ---> " + getAllCompanyCoupons());
            System.err.println("Getting all coupons by a category ---> " + getCouponsByCategory());
            System.err.println("Getting all coupons by a max price ---> " + getCompanyCouponsByPrice());
            System.err.println("Getting a company ---> " + getCompany());

        } catch (Exception e) {
            System.err.println("Company Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.err.println(TestColorsConstants.ANSI_CYAN + "Company Test has been passed successfully!" + TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Company Register Test --> -----------------------------------------------

    public boolean companyRegister(CompanyDto companyDto) throws ApplicationException {

        try {

        //Setting the jwt token with company credentials
        jwt = authController.registerCompany(companyDto);

    } catch (Exception e) {
        System.err.println("Company Registration Test: " + false);
        throw new ApplicationException(e.getMessage());
    }
        System.out.println(TestColorsConstants.ANSI_ORANGE +
                "The company : " + companyDto.getName() + "," + " Has been registered and logged in successfully!" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Company Login Test --> -----------------------------------------------

    public boolean companyLogin(String email, String password) throws ApplicationException {

        //Getting a company from database
        Company company = companyRepository.findByEmail(email);

        //Mapping the company object to dto object
        CompanyDto companyDto = ObjectMappingUtil.companyToCompanyDto(company);

        //Setting the dto company password to the inserted one
        companyDto.setPassword(password);

        try {
            //Setting the jwt token with company credentials
            jwt = authController.login(companyDto);

            //Building a response entity of company and inserting the token to the request header and setting a body
            ResponseEntity<UserDto> newCompany = JwtHeaderPlacementUtil.
                    setHeaderToPostOrUpdate(jwt, companyDto);

            restTemplate.postForEntity(TestUrlConstants.LOGIN_USER, newCompany, Void.class);

        } catch (Exception e) {
            System.err.println("Company Login Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        System.out.println(TestColorsConstants.ANSI_ORANGE + "The company : " +companyDto.getName() + "," + " Has been logged in successfully!" +
                TestColorsConstants.ANSI_DEFAULT_RESET);
        return true;
    }

    //----------------------------------- Coupons creation --> ---------------------------------------------------

    public boolean createCoupons() throws ApplicationException {

        CouponDto couponDto1 = CouponDto.builder().
                companyId(2L).
                category(CouponCategory.MEDICINE).
                title("Kalevet Vaccination").
                description("Work!").
                startDate(LocalDate.of(2022, 4, 5)).
                endDate(LocalDate.of(2022, 12, 5)).
                amount(2000).
                price(50.0).
                imageUrl("https://www.dmu.ac.uk/webimages/DMU-students/Hot-topics/2021/June/covid-vaccine-main.jpg").
                build();

        CouponDto couponDto11 = CouponDto.builder().
                companyId(2L).
                category(CouponCategory.MEDICINE).
                title("Hatelet").
                description("Til!").
                startDate(LocalDate.of(2022, 4, 5)).
                endDate(LocalDate.of(2022, 12, 5)).
                amount(150).
                price(80.0).
                imageUrl("https://www.dmu.ac.uk/webimages/DMU-students/Hot-topics/2021/June/covid-vaccine-main.jpg").
                build();

        CouponDto couponDto111 = CouponDto.builder().
                companyId(2L).
                category(CouponCategory.MEDICINE).
                title("Esh").
                description("Work!").
                startDate(LocalDate.of(2022, 4, 5)).
                endDate(LocalDate.of(2022, 12, 5)).
                amount(700).
                price(60.0).
                imageUrl("https://www.dmu.ac.uk/webimages/DMU-students/Hot-topics/2021/June/covid-vaccine-main.jpg").
                build();

        CouponDto couponDto2 = CouponDto.builder().
                companyId(3L).
                category(CouponCategory.FASHION).
                title("Jeans").
                description("pants").
                startDate(LocalDate.of(2022, 2, 28)).
                endDate(LocalDate.of(2022, 12, 10)).
                amount(1).
                price(78.5).
                imageUrl("https://media.glamour.com/photos/5d9f460e01ef71000961d240/master/w_1000,h_1250,c_limit/madewell.jpg").
                build();

        CouponDto couponDto3 = CouponDto.builder().
                companyId(3L).
                category(CouponCategory.FOOD).
                title("Lamburger").
                description("Til").
                startDate(LocalDate.of(2022, 2, 28)).
                endDate(LocalDate.of(2022, 12, 10)).
                amount(32).
                price(70.5).
                imageUrl("https://media-cdn.tripadvisor.com/media/photo-s/17/3b/9a/d2/burger-king.jpg").
                build();

        CouponDto couponDto4 = CouponDto.builder().
                companyId(3L).
                category(CouponCategory.FOOD).
                title("Orez").
                description("Madhim").
                startDate(LocalDate.of(2022, 4, 17)).
                endDate(LocalDate.of(2022, 11, 28)).
                amount(5).
                price(15.0).
                imageUrl("https://www.sugat.com/wp-content/uploads/2016/04/clasi5.jpg").
                build();


        //Building a response entity of coupons and inserting the token to the request header and setting a body
        ResponseEntity<CouponDto> newCoupon = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto11);
        ResponseEntity<CouponDto> newCoupon0 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto111);
        ResponseEntity<CouponDto> newCoupon1 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto1);
        ResponseEntity<CouponDto> newCoupon2 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto2);
        ResponseEntity<CouponDto> newCoupon3 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto3);
        ResponseEntity<CouponDto> newCoupon4 = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto4);

        try {

                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                            newCoupon,
                            CouponDto.class);

            restTemplate.postForEntity(
                    TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                    newCoupon0,
                    CouponDto.class);

            restTemplate.postForEntity(
                    TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                    newCoupon1,
                    CouponDto.class);


                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                            newCoupon2,
                            CompanyDto.class);


                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                            newCoupon3,
                            CompanyDto.class);


                    restTemplate.postForEntity(
                            TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                            newCoupon4,
                            CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Creating coupons Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Updating a coupon --> ---------------------------------------------------

    public boolean updateCoupon() throws ApplicationException {

        CouponDto couponDto = CouponDto.builder().
                id(2L).
                amount(300).
                build();

        //Building a response entity of coupon and inserting the token to the request header and setting a body
        ResponseEntity<CouponDto> newCoupon = JwtHeaderPlacementUtil.setHeaderToPostOrUpdateCoupon(jwt, couponDto);

        try {
            restTemplate.exchange(
                    TestUrlConstants.POST_OR_UPDATE_COUPON_URL,
                    HttpMethod.PUT,
                    newCoupon,
                    CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

          //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Updating coupon Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Coupon deletion --> ---------------------------------------------------

    public boolean deleteCoupon() throws ApplicationException {

        try {
            restTemplate.exchange(TestUrlConstants.COUPON_DELETION_URL + "3",
                    HttpMethod.DELETE,
                    //Inserting the token to the request header
                    JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                    CompanyDto.class);

            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);
            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Deleting a coupon Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting a coupon --> ---------------------------------------------------

    public boolean getCoupon() throws ApplicationException {

        try {
            //Setting a response entity of coupon dto array and activating the controller get method
            ResponseEntity<CouponDto> couponDto = restTemplate.
                    exchange(TestUrlConstants.GET_COUPON_URL + "1",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CouponDto.class);

            //Getting a coupon body
            CouponDto newCoupon = couponDto.getBody();
            System.err.println(newCoupon);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting a coupons Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all company coupons --------------------------------------------

    public boolean getAllCompanyCoupons() throws ApplicationException {

        try {
            //Setting a response entity of coupons list dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange(TestUrlConstants.GETTING_ALL_COMPANY_COUPONS_URL + 2,
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
            System.err.println("Getting all company coupons Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }

    //----------------------------------- Getting all coupons by category -----------------------------------------

    public boolean getCouponsByCategory() throws ApplicationException {

        try {
            //Setting a response entity of coupons list dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange("http://localhost:8080/company/coupons-by-category/3?category=FASHION",
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

    //----------------------------------- Getting all coupons by price ------------------------------------------

    public boolean getCompanyCouponsByPrice() throws ApplicationException {

        try {
            //Setting a response entity of coupons dto and activating the controller get all method
            ResponseEntity<CouponListDtoWrapper> coupons = restTemplate.
                    exchange("http://localhost:8080/company/get-by-price/3?maxPrice=950.0",
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

    //----------------------------------- Getting a specific company ---------------------------------------------

    public boolean getCompany() throws ApplicationException {

        try {
            //Setting a response entity of company dto and activating the controller get method
            ResponseEntity<CompanyDto> company = restTemplate.
                    exchange(TestUrlConstants.GET_COMPANY_URL + "3",
                            HttpMethod.GET,
                            //Inserting the token to the request header
                            JwtHeaderPlacementUtil.setHeaderToGetOrDelete(jwt),
                            CompanyDto.class);

            //Getting a company body
            CompanyDto newCompany = company.getBody();
            System.err.println(newCompany);
            log.info(TestColorsConstants.ANSI_YELLOW_BACKGROUND + TestColorsConstants.ANSI_BLACK + "Test Passed!" +
                    TestColorsConstants.ANSI_DEFAULT_RESET);

            //Catching all exceptions
        } catch (Exception e) {
            System.err.println("Getting a company Test: " + false);
            throw new ApplicationException(e.getMessage());
        }
        return true;
    }
}
