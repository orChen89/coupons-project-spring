package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.JwtDto;
import com.or.couponsproject.couponsproject.dto.UserDto;
import com.or.couponsproject.couponsproject.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public JwtDto authenticate(UserDto userDto) {

        try {

            System.out.println(userDto);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), String.valueOf(userDto.getPassword().hashCode()))
            );
        } catch (final BadCredentialsException e) {
            throw new RuntimeException("Incorrect credentials");
        }
        return new JwtDto(JwtUtil.generateToken(userDto.getEmail()));
    }
}
