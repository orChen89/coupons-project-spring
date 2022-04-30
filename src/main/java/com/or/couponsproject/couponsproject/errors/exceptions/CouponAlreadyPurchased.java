package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.model.Coupon;

public class CouponAlreadyPurchased extends ApplicationException {

    public CouponAlreadyPurchased(final EntityType entityType, final Coupon coupon, final EntityType entityType2) {
        super(entityType + ": " + coupon.getTitle() + " with id: " + coupon.getId() +
              " has already been purchased by this " + entityType2);
    }
}
