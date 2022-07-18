package com.or.couponsproject.couponsproject.controllers;

import com.or.couponsproject.couponsproject.dto.JwtDto;
import com.or.couponsproject.couponsproject.dto.UserDto;
import com.or.couponsproject.couponsproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("login")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public JwtDto authenticate(@RequestBody final UserDto user) {
        return authService.authenticate(user);
    }
}
