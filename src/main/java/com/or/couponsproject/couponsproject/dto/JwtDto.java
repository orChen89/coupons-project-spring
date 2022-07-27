package com.or.couponsproject.couponsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDto {

    //Returning a string of jwt token
    private String jwt;
}
