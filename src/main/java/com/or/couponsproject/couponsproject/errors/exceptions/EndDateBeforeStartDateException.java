package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.errors.Constraint;

public class EndDateBeforeStartDateException extends ApplicationException {

    public EndDateBeforeStartDateException(final Constraint constraint) {
        super(constraint.getErrorMsg());
    }
}
