package com.or.couponsproject.couponsproject.util;

import com.or.couponsproject.couponsproject.model.Coupon;
import java.time.LocalDate;

import static com.or.couponsproject.couponsproject.constants.Constants.REDUCE_AMOUNY_BY_ONE;

public class CouponUtil {

    //This method is decreasing a specific coupon amount by 1
    public static void decreaseCouponAmount(final Coupon coupon) {
        int prevAmount = coupon.getAmount();
        coupon.setAmount(prevAmount - REDUCE_AMOUNY_BY_ONE);
    }

    //This method is checking if a coupon end date is expired
    public static boolean isCouponExpired(final LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

}
