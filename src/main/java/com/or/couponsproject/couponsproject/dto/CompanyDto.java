package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CompanyDto extends UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private List<CouponDto> coupons;
    private Role role;
}
