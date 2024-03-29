package com.or.couponsproject.couponsproject.util;

import com.or.couponsproject.couponsproject.dto.*;
import com.or.couponsproject.couponsproject.model.Admin;
import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMappingUtil {

    public static Admin adminDtoToEntity(final AdminDto dto) {

        //Converting an admin dto to Admin object
        return Admin.builder().
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                role(dto.getRole()).
                build();
    }

    public static Coupon couponDtoToCoupon(final CouponDto dto) {

        //Converting a coupon dto object to coupon object
        return Coupon.builder().
                company(Company.builder().id(dto.getCompanyId()).build()).
                title(dto.getTitle()).
                category(dto.getCategory()).
                description(dto.getDescription()).
                startDate(dto.getStartDate()).
                endDate(dto.getEndDate()).
                amount(dto.getAmount()).
                price(dto.getPrice()).
                image(dto.getImageUrl()).
                build();
    }

    public static Coupon couponDtoToCouponUpdate(final CouponDto dto) {

        //Converting a coupon dto object to coupon object and forcing entering id when performing update
        return Coupon.builder().
                id(dto.getId()).
                company(Company.builder().id(dto.getCompanyId()).build()).
                title(dto.getTitle()).
                category(dto.getCategory()).
                description(dto.getDescription()).
                startDate(dto.getStartDate()).
                endDate(dto.getEndDate()).
                amount(dto.getAmount()).
                price(dto.getPrice()).
                image(dto.getImageUrl()).
                build();
    }

    public static Company companyDtoToCompany(final CompanyDto dto) {

        //Converting a company dto object to company object
        return Company.builder().
                name(dto.getName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                role(dto.getRole()).
                build();
    }

    public static Company companyDtoToCompanyUpdate(final CompanyDto dto) {

        //Converting a company dto object to company object and forcing entering id when performing update
        return Company.builder().
                id(dto.getId()).
                name(dto.getName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                role(dto.getRole()).
                build();
    }

    public static Company companyDtoToCompanyUpdateWhenNoPass(final CompanyDto dto) {

        //Converting a company dto object to company object when performing an update operation with no password
        return Company.builder().
                id(dto.getId()).
                name(dto.getName()).
                email(dto.getEmail()).
                password(Integer.valueOf(dto.getPassword())).
                role(dto.getRole()).
                build();
    }

    public static Customer customerDtoToCustomer(final CustomerDto dto) {

        //Converting a customer dto object to customer object
        return Customer.builder().
                firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                role(dto.getRole()).
                build();
    }

    public static Customer customerDtoToCustomerUpdate(final CustomerDto dto) {

        //Converting a customer dto object to customer object and forcing entering id when performing update
        return Customer.builder().
                id(dto.getId()).
                firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                email(dto.getEmail()).
                password(dto.getPassword().hashCode()).
                role(dto.getRole()).
                build();
    }

    public static Customer customerDtoToCustomerUpdateWhenNoPass(final CustomerDto dto) {

        //Converting a customer dto object to customer object when performing an update operation with no password
        return Customer.builder().
                id(dto.getId()).
                firstName(dto.getFirstName()).
                lastName(dto.getLastName()).
                email(dto.getEmail()).
                password(Integer.valueOf(dto.getPassword())).
                role(dto.getRole()).
                build();
    }

    public static CustomerDto customerToCustomerDto(final Customer customer) {

        //Converting customer to a dto object
        return CustomerDto.builder().
                id(customer.getId()).
                firstName(customer.getFirstName()).
                lastName(customer.getLastName()).
                email(customer.getEmail()).
                password(String.valueOf(customer.getPassword())).
                coupons(couponsToCouponsDto(customer.getCoupons())).
                role(customer.getRole()).
                build();
    }

    public static CompanyDto companyToCompanyDto(final Company company) {

        //Converting company to a dto object
        return CompanyDto.builder().
                id(company.getId()).
                name(company.getName()).
                email(company.getEmail()).
                password(String.valueOf(company.getPassword())).
                coupons(couponsToCouponsDto(company.getCoupons())).
                role(company.getRole()).
                build();
    }

    public static CouponDto couponToCouponDto(final Coupon coupon) {

        //Converting coupon to a dto object
        return CouponDto.builder().
                id(coupon.getId()).
                title(coupon.getTitle()).
                companyId(coupon.getCompany().getId()).
                category(coupon.getCategory()).
                description(coupon.getDescription()).
                startDate(coupon.getStartDate()).
                endDate(coupon.getEndDate()).
                amount(coupon.getAmount()).
                price(coupon.getPrice()).
                imageUrl(coupon.getImage()).
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

    public static List<CouponDto> couponsToCouponsDtoWrapper(final List<Coupon> coupons) {

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
    public static List<CompanyDto> companiesToCompaniesDto(final List<Company> companies) {

        List<CompanyDto> dtoList = new ArrayList<>();

        if (companies != null) {
            for (Company c : companies) {
                //Concerting each company from the inserted companies list to dto company object
                CompanyDto companyDto = companyToCompanyDto(c);
                //Adding the converted company to list of dto companies
                dtoList.add(companyDto);
            }
        }
        return dtoList;
    }

    public static List<CustomerDto> customersToCustomersDto(final List<Customer> customers) {

        List<CustomerDto> dtoList = new ArrayList<>();

        if (customers != null) {
            for (Customer c : customers) {
                //Concerting each customer from the inserted customers list to dto customer object
                CustomerDto customerDto = customerToCustomerDto(c);
                //Adding the converted customer to list of dto customers
                dtoList.add(customerDto);
            }
        }
        return dtoList;
    }

    //This method is setting a coupon when performing a coupon update once not entering coupon parameters
    public static CouponDto mappingCouponWhenUpdate(final CouponDto couponDto, final Coupon coupon) {

        if (couponDto.getTitle() == null) {
            couponDto.setTitle(coupon.getTitle());
        }

        if (couponDto.getPrice() == null) {
            couponDto.setPrice(coupon.getPrice());
        }

        if (couponDto.getCategory() == null) {
            couponDto.setCategory(coupon.getCategory());
        }

        if (couponDto.getStartDate() == null) {
            couponDto.setStartDate(coupon.getStartDate());
        }

        if (couponDto.getAmount() == null) {
            couponDto.setAmount(coupon.getAmount());
        }

        if (couponDto.getEndDate() == null) {
            couponDto.setEndDate(coupon.getEndDate());
        }

        if (couponDto.getDescription() == null) {
            couponDto.setDescription(coupon.getDescription());
        }

        if (couponDto.getCompanyId() == null) {
            couponDto.setCompanyId(coupon.getCompany().getId());
        }

        if (couponDto.getImageUrl() == null) {
            couponDto.setImageUrl(coupon.getImage());
        }
        return couponDto;
    }

}
