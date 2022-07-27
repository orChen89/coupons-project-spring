package com.or.couponsproject.couponsproject.task;

import com.or.couponsproject.couponsproject.clr.TestColorsConstants;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.model.Coupon;
import com.or.couponsproject.couponsproject.repo.CouponRepository;
import com.or.couponsproject.couponsproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
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

        log.info(TestColorsConstants.ANSI_GREEN + "Thread: " + Thread.currentThread().getName() + " has started to run" +
                TestColorsConstants.ANSI_DEFAULT_RESET);

        //Getting all coupons with the specific end date of today from the database
        List<Coupon> coupons = couponRepository.findByEndDate(LocalDate.now());

        //Deleting the specific coupon that has been expired
        for (Coupon coupon : coupons) {

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

