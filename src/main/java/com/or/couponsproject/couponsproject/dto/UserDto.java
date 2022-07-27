package com.or.couponsproject.couponsproject.dto;

import com.or.couponsproject.couponsproject.enums.Role;
import lombok.*;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private String password;
    private Role role;

    public UserDto(Long id, String email, String name, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public UserDto(Long id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
