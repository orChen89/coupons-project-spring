package com.or.couponsproject.couponsproject.errors.exceptions;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

public class LoginMatchingEntityException extends ApplicationException {

    public LoginMatchingEntityException(final String email, final RedisProperties.ClientType clientType) {
        super("Failed to authenticate: " + " The current " + clientType + "'s " + email +
                " is not matching to entity's login credentials! ");
    }
}
