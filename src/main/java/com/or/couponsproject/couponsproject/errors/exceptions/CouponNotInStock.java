package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.model.Coupon;

public class CouponNotInStock extends ApplicationException {

    public CouponNotInStock(final EntityType entityType, final Coupon coupon) {
        super(entityType + ": " + coupon.getTitle() + " with id number: " + coupon.getId() +"," +  " is currently not in stock");
    }
}
