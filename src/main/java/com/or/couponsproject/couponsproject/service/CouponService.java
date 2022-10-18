package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<CouponDto> fetchAllCoupons() {

        List<Coupon> allCoupons = couponRepository.findAll();

        //Converting the coupons to a Dto coupon list
        return ObjectMappingUtil.couponsToCouponsDto(allCoupons);
    }
}
