package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.CompanyDto;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("company")
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createCoupon(@RequestBody final CouponDto coupon) throws ApplicationException {
        companyService.createCoupon(coupon);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public void updateCoupon(@RequestBody final CouponDto coupon) throws ApplicationException {
        companyService.updateCoupon(coupon);
    }

    @DeleteMapping("{couponId}")
    public void deleteCoupon(@PathVariable final long couponId) throws ApplicationException {
        companyService.deleteCoupon(couponId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("{couponId}")
    public CouponDto getCoupon(@PathVariable final long couponId) throws ApplicationException {
        return companyService.getCoupon(couponId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("companyCoupons/{companyId}")
    public List<CouponDto> getAllCouponsByCompanyId(@PathVariable final long companyId) throws ApplicationException {
        return companyService.getAllCoupons(companyId);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("getByCategory/{companyId}/{category}")
    public List<CouponDto> getCouponsByCategory(@PathVariable(name = "companyId") final long companyId,
                                                @PathVariable(name = "category")
                                                final CouponCategory couponCategory) throws ApplicationException {
        return companyService.getCouponsByCategory(companyId, couponCategory);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("getByMaxPrice/{companyId}/{maxPrice}")
    public List<CouponDto> getCouponsByMaxPrice(@PathVariable(name = "companyId") final Long companyId,
                                                @PathVariable(name = "maxPrice")
                                                final double maxPrice) throws ApplicationException {
        return companyService.getCouponsByMaxPrice(companyId, maxPrice);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("getCompany/{companyId}")
    public CompanyDto getCompany(@PathVariable final long companyId) throws ApplicationException {
        return companyService.getCompany(companyId);
    }
}

