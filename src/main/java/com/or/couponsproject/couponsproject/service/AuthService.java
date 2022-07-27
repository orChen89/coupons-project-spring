package com.or.couponsproject.couponsproject.service;

import com.or.couponsproject.couponsproject.dto.JwtDto;
import com.or.couponsproject.couponsproject.dto.UserDto;
import com.or.couponsproject.couponsproject.errors.exceptions.ApplicationException;
import com.or.couponsproject.couponsproject.security.JwtService;
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
    private final JwtService jwtService;

    //Generating and returning a jwt token to specific user according to email and password
    public JwtDto authenticate(UserDto userDto) throws ApplicationException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), String.valueOf(userDto.getPassword().hashCode()))
            );
        } catch (final BadCredentialsException e) {
            throw new ApplicationException("Incorrect email Or password");
        }
        return new JwtDto(JwtUtil.generateToken(jwtService.loadUserByUsername(userDto.getEmail())));
    }
}
