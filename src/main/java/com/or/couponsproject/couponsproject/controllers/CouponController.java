package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CouponListDtoWrapper;
import com.or.couponsproject.couponsproject.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("coupons")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CouponController {

    private final CouponService couponService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public CouponListDtoWrapper fetchAllCoupons() {
        return new CouponListDtoWrapper(couponService.fetchAllCoupons());
    }
}
