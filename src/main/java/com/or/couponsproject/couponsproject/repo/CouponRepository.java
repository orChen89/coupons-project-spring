package com.or.couponsproject.couponsproject.repo;

import com.or.couponsproject.couponsproject.enums.CouponCategory;
import com.or.couponsproject.couponsproject.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository <Coupon, Long> {

    //Getting a list of coupons according to specific company
    List<Coupon> getCouponsByCompanyId (Long companyId);

    //Getting a list of coupons according to specific customer
    List<Coupon> getCouponsByCustomersId (Long customerId);

    //Checking a coupon if exists according to it's title
    boolean existsByTitle(String title);

    //Getting a list of coupons according to specific title of coupon
    List<Coupon> findByTitle(String title);

    //Getting a list of coupons according to specific coupon End date
    List<Coupon> findByEndDate(LocalDate endDate);

    //Getting a list of coupons according to specific category of specific company
    List<Coupon> findByCategoryAndCompanyId(CouponCategory category, Long companyId);

}
