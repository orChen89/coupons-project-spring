package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.errors.Constraint;

public class UserValidationException extends ApplicationException {

    public UserValidationException(final Constraint constraint){
        super(constraint.getErrorMsg());
    }
}
