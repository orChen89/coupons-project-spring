package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("purchase/customerId/{customerId}/couponId/{couponId}")
    public void createCouponPurchase(@PathVariable(name = "customerId") final long customerId,
                                     @PathVariable(name = "couponId") final long couponId) throws ApplicationException {
        customerService.addCouponPurchase(customerId, couponId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/getCustomerCoupons/{customerId}")
    public List<CouponDto> getAllCouponsByCustomerId(@PathVariable final long customerId) throws ApplicationException {
        return customerService.getAllCouponsByCustomerId(customerId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/getCustomer/{customerId}")
    public CustomerDto getCustomer(@PathVariable final long customerId) throws ApplicationException {
        return customerService.getCustomer(customerId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/getCouponsByCategory/customerId/{customerId}/category/{category}")
    public List<CouponDto> getCouponsByCategory(@PathVariable(name = "customerId") final long customerId,
                                                @PathVariable(name = "category") final CouponCategory couponCategory)
                                                throws ApplicationException {
        return customerService.getCouponsByCategory(customerId, couponCategory);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("getCouponsByMaxPrice/customerId/{customerId}/maxPrice/{maxPrice}")
    public List<CouponDto> getCouponsByMaxPrice(@PathVariable(name = "customerId") final Long customerId,
                                                @PathVariable(name = "maxPrice") final double maxPrice)
                                                throws ApplicationException {
        return customerService.getCouponsByMaxPrice(customerId, maxPrice);
    }
}
