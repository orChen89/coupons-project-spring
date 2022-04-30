package com.or.couponsproject.couponsproject.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Constraint {

    START_DATE_AT_OR_BEFORE_END_DATE("End date cannot be before startDate!"),

    INVALID_DATE("The inserted date of current coupon is invalid"),

    ENTITY_NOT_EXISTS(" is not exists!"),

    ENTITY_ALREADY_EXISTS(" is already exists!"),

    INVALID_INPUT_FORMAT("Invalid input - Please enter according to the correct format"),

    ENTITY_NAME_EXISTS("This Company's name is already exists! It is not allowed to update a company name!");

    @Getter
    private final String errorMsg;
}
