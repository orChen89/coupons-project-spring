package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository <Coupon, Long> {

    List<Coupon> getCouponsByCompanyId (Long companyId);

    List<Coupon> getCouponsByCustomersId (Long couponId);

}
