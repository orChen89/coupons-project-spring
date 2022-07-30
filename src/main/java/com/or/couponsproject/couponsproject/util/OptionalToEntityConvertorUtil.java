package com.or.couponsproject.couponsproject.util;

import com.or.couponsproject.couponsproject.model.Company;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionalToEntityConvertorUtil {

    public static Company optionalCompany(final Optional<Company> company) {

        return company.orElse(null);
    }

    public static Customer optionalCustomer(final Optional<Customer> customer) {

        return customer.orElse(null);
    }

    public static Coupon optionalCoupon(final Optional<Coupon> coupon) {

        return coupon.orElse(null);
    }
}
