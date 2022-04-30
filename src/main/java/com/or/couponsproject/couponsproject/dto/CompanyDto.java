package com.or.couponsproject.couponsproject.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<CouponDto> coupons = new ArrayList<>();
}
