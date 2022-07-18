package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AdminDto extends UserDto {

    private Long id;
    private String email;
    private String password;
    private Role role;

}
