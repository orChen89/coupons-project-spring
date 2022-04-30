package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.errors.Constraint;

public class EmailAlreadyInUse extends ApplicationException{

    public EmailAlreadyInUse(final Constraint constraint) {
        super("This Email address" + constraint.getErrorMsg() + " and in use");
    }
}
