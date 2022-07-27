package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.CouponListDtoWrapper;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.CompanyService;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("company")
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CouponDto createCoupon(@RequestBody final CouponDto coupon) throws ApplicationException {
       return ObjectMappingUtil.couponToCouponDto(companyService.createCoupon(coupon));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public void updateCoupon(@RequestBody final CouponDto coupon) throws ApplicationException {
        companyService.updateCoupon(coupon);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{couponId}")
    public void deleteCoupon(@PathVariable final long couponId) throws ApplicationException {
        companyService.deleteCoupon(couponId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coupon/{couponId}")
    public CouponDto getCoupon(@PathVariable final long couponId) throws ApplicationException {
        return companyService.getCoupon(couponId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-coupons/{companyId}")
    public CouponListDtoWrapper getAllCouponsByCompanyId(@PathVariable final long companyId) throws ApplicationException {
        return new CouponListDtoWrapper(companyService.getAllCoupons(companyId));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/coupons-by-category/{companyId}")
    public CouponListDtoWrapper getCouponsByCategory(@PathVariable(name = "companyId") final long companyId,
                                                @RequestParam(name = "category")
                                                final CouponCategory couponCategory) throws ApplicationException {
        return new CouponListDtoWrapper(companyService.getCouponsByCategory(companyId, couponCategory));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-by-price/{companyId}")
    public CouponListDtoWrapper getCouponsByMaxPrice(@PathVariable(name = "companyId") final Long companyId,
                                                @RequestParam(name = "maxPrice")
                                                final double maxPrice) throws ApplicationException {
        return new CouponListDtoWrapper(companyService.getCouponsByMaxPrice(companyId, maxPrice));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{companyId}")
    public CompanyDto getCompany(@PathVariable final long companyId) throws ApplicationException {
        return companyService.getCompany(companyId);
    }
}

