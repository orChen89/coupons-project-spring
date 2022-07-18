package com.or.couponsproject.couponsproject.dto;

import lombok.*;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;
    private String password;

}
