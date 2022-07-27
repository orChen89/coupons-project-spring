package com.or.couponsproject.couponsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CouponListDtoWrapper {

        //Returning a list of coupons as an object
        private List<CouponDto> couponDtoList;
}
