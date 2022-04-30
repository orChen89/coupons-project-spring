package com.or.couponsproject.couponsproject.util;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import java.util.ArrayList;
import java.util.List;

public class ObjectMappingUtil {

    public static Coupon couponDtoToCoupon(final CouponDto dto) {

        //Converting a coupon dto object to coupon object
        return Coupon.builder().
                id(dto.getId()).
                company(Company.builder().id(dto.getCompanyID()).build()).
                title(dto.getTitle()).
                category(dto.getCategory()).
                description(dto.getDescription()).
                startDate(dto.getStartDate()).
                endDate(dto.getEndDate()).
                amount(dto.getAmount()).
                price(dto.getPrice()).
                image(dto.getImage()).
                build();
    }

    public static Coupon couponDtoToCouponUpdate(final CouponDto dto) {

        //Converting a coupon dto object to coupon object
        return Coupon.builder().
                id(dto.getId()).
                company(Company.builder().id(dto.getCompanyID()).build()).
                title(dto.getTitle()).
                category(dto.getCategory()).
                description(dto.getDescription()).
                startDate(dto.getStartDate()).
                endDate(dto.getEndDate()).
                amount(dto.getAmount()).
                price(dto.getPrice()).
                image(dto.getImage()).
                build();
    }

    public static Company companyDtoToCompany(final CompanyDto dto) {

        //Converting a company dto object to company object
        return Company.builder().
                name(dto.getName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                build();
    }

    public static Company companyDtoToCompanyUpdate(final CompanyDto dto) {

        //Converting a company dto object to company object when performing an update operation
        return Company.builder().
                id(dto.getId()).
                name(dto.getName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                build();
    }

    public static Customer customerDtoToCustomer(final CustomerDto dto) {

        //Converting a customer dto object to customer object
        return Customer.builder().
                firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                build();
    }

    public static Customer customerDtoToCustomerUpdate(final CustomerDto dto) {

        //Converting a customer dto object to customer object when performing an update operation
        return Customer.builder().
                id(dto.getId()).
                firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                build();
    }

    public static CustomerDto customerToCustomerDto(final Customer customer) {

        //Converting customer to a dto object
        return CustomerDto.builder().
                id(customer.getId()).
                firstName(customer.getFirstName()).
                lastName(customer.getLastName()).
                email(customer.getEmail()).
                coupons(couponsToCouponsDto(customer.getCoupons())).
                build();
    }

    public static CompanyDto companyToCompanyDto(final Company company) {

        //Converting company to a dto object
        return CompanyDto.builder().
                id(company.getId()).
                name(company.getName()).
                email(company.getEmail()).
                coupons(couponsToCouponsDto(company.getCoupons())).
                build();
    }

    public static CouponDto couponToCouponDto(final Coupon coupon) {

        //Converting coupon to a dto object
        return CouponDto.builder().
                id(coupon.getId()).
                title(coupon.getTitle()).
                companyID(coupon.getCompany().getId()).
                category(coupon.getCategory()).
                description(coupon.getDescription()).
                startDate(coupon.getStartDate()).
                endDate(coupon.getEndDate()).
                amount(coupon.getAmount()).
                price(coupon.getPrice()).
                image(coupon.getImage()).
                build();
    }

    public static List<CouponDto> couponsToCouponsDto(final List<Coupon> coupons) {

        List<CouponDto> dtoList = new ArrayList<>();

        if (coupons != null) {
            for (Coupon c : coupons) {
                //Concerting each coupon from the inserted coupons list to dto coupon object
                CouponDto couponDto = couponToCouponDto(c);
                //Adding the converted coupon to list of dto coupons
                dtoList.add(couponDto);
            }
        }
        return dtoList;
    }

    public static List<Coupon> couponsDtoToCoupons(final List<CouponDto> coupons) {

        List<Coupon> couponsList = new ArrayList<>();

        if (coupons != null) {
            for (CouponDto c : coupons) {
                //Concerting each coupon from the inserted Dto coupons list to a coupon object
                Coupon coupon = couponDtoToCoupon(c);
                //Adding the converted coupon to list of coupons
                couponsList.add(coupon);
            }
        }
        return couponsList;
    }
}
