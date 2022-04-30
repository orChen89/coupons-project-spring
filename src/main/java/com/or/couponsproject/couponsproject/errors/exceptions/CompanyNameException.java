package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.errors.Constraint;

public class CompanyNameException extends ApplicationException {

    public CompanyNameException(final Constraint constraint) {

        super(constraint.getErrorMsg());
    }
}
