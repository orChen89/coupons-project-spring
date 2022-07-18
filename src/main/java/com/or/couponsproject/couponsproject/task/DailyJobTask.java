package com.or.couponsproject.couponsproject.task;

import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.service.CompanyService;
import com.or.couponsproject.couponsproject.util.CouponUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

import static com.or.couponsproject.couponsproject.constants.Constants.TWENTY_FOUR_HOURS;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class DailyJobTask {

    private final CouponRepository couponRepository;
    private final CompanyService companyService;

    @Scheduled(fixedDelay = TWENTY_FOUR_HOURS)
    public void deleteExpiredCoupons() {

        List<Coupon> coupons = couponRepository.findAll();

        log.info("Thread: " + Thread.currentThread().getName() + " started to run");

        for (Coupon coupon : coupons) {

            if (CouponUtil.isCouponExpired(coupon.getEndDate())) {

                try {
                    companyService.deleteCoupon(coupon.getId());
                } catch (ApplicationException e) {
                    log.error(e.getMessage());
                }

                log.info("The coupon: " + coupon.getTitle() +
                        " has been deleted due to its expiration date: " + coupon.getEndDate());
            }
        }
    }
}
