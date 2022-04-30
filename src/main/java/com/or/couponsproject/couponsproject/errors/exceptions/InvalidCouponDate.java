package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.errors.Constraint;

public class InvalidCouponDate extends ApplicationException {

    public InvalidCouponDate(final Constraint constraint) {
        super(constraint.getErrorMsg());
    }
}
