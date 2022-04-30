package com.or.couponsproject.couponsproject.errors.exceptions;

import com.or.couponsproject.couponsproject.enums.EntityType;
import com.or.couponsproject.couponsproject.errors.Constraint;

public class EntityExistException extends ApplicationException {
    public EntityExistException(final EntityType entityType, final Constraint constraint){

        super("This " + entityType + constraint.getErrorMsg());
    }
}
