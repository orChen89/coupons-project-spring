package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.model.Coupon;
import lombok.*;

import java.util.List;

@Data
@ToString(exclude = "password")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<CouponDto> coupons;

    public CustomerDto(Long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public CustomerDto(Long id) {
        this.id = id;
    }

    public CustomerDto(String firstName, String lastName, String email, String password, List<CouponDto> coupons) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.coupons= coupons;
    }
}
