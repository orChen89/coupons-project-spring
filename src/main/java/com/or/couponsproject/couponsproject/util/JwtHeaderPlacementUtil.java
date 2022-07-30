package com.or.couponsproject.couponsproject.util;


import com.or.couponsproject.couponsproject.constants.Constants;
import com.or.couponsproject.couponsproject.dto.CouponDto;
import com.or.couponsproject.couponsproject.dto.JwtDto;
import com.or.couponsproject.couponsproject.dto.UserDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtHeaderPlacementUtil {

    private final static HttpHeaders header = new HttpHeaders();

    //Setting the header with jwt token & returning a Http object with the header
    public static HttpEntity<Void> setHeaderToGetOrDelete(JwtDto jwt) {

        header.set(Constants.HEADER_KEY, Constants.BEARER_AUTH_SCHEME_START + jwt.getJwt());

        return new HttpEntity<>(header);
    }

    //Setting the header with jwt token & inserting the specific user body to the request
    public static ResponseEntity<UserDto> setHeaderToPostOrUpdate(JwtDto jwt, UserDto user) {

        return ResponseEntity.ok()
                .header(Constants.HEADER_KEY, Constants.BEARER_AUTH_SCHEME_START + jwt.getJwt())
                .body(user);
    }

    //Setting the header with jwt token & inserting the specific coupon body to the request
    public static ResponseEntity<CouponDto> setHeaderToPostOrUpdateCoupon(JwtDto jwt, CouponDto couponDto) {

        return ResponseEntity.ok()
                .header(Constants.HEADER_KEY, Constants.BEARER_AUTH_SCHEME_START + jwt.getJwt())
                .body(couponDto);
    }
}
