package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.model.Coupon;

public class CouponExpirationDateArrived extends ApplicationException {

    public CouponExpirationDateArrived(final Coupon coupon) {
        super("This coupon: " + coupon.getTitle() + ", end date," + " is expired!");
    }
}
