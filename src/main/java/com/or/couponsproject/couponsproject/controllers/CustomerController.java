package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CouponListDtoWrapper;
import com.or.couponsproject.couponsproject.dto.CustomerDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{customerId}/{couponId}")
    public void createCouponPurchase(@PathVariable(name = "customerId") final long customerId,
                                     @PathVariable(name = "couponId") final long couponId) throws ApplicationException {
        customerService.addCouponPurchase(customerId, couponId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-coupons/{customerId}")
    public CouponListDtoWrapper getAllCouponsByCustomerId(@PathVariable final long customerId) throws ApplicationException {
        return new CouponListDtoWrapper(customerService.getAllCouponsByCustomerId(customerId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable final long customerId) throws ApplicationException {
        return customerService.getCustomer(customerId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coupons-by-category/{customerId}")
    public CouponListDtoWrapper getCouponsByCategory(@PathVariable(name = "customerId") final long customerId,
                                                     @RequestParam(name = "category") final CouponCategory couponCategory)
                                                throws ApplicationException {
        return new CouponListDtoWrapper(customerService.getCouponsByCategory(customerId, couponCategory));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-price/{customerId}")
    public CouponListDtoWrapper getCouponsByMaxPrice(@PathVariable(name = "customerId") final Long customerId,
                                                     @RequestParam(name = "maxPrice") final double maxPrice)
                                                throws ApplicationException {
        return new CouponListDtoWrapper(customerService.getCouponsByMaxPrice(customerId, maxPrice));
    }
}
