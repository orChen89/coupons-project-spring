package com.or.couponsproject.couponsproject.security;

import com.or.couponsproject.couponsproject.constants.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;



    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        //Checking if header is set with key and value of Bearer and getting the specific email
        if (authorizationHeader != null && authorizationHeader.startsWith(Constants.BEARER_AUTH_SCHEME_START)) {
            final String jwt = authorizationHeader.substring(Constants.TOKEN_START_INDEX);
            final String email = JwtUtil.extractEmail(jwt);

            //Checking if email is not null and loading a specific user according to the email
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(email);

                //Checking if token is valid according to specific user and getting auth of user
                if (JwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    //Setting Authentication with request and token of user
                    upaToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upaToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
