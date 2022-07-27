package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;

import java.util.List;

@Data
@ToString(exclude = "password")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto extends UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<CouponDto> coupons;
    private Role role;
}
