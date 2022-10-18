package com.or.couponsproject.couponsproject.security;

import com.or.couponsproject.couponsproject.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtil {

    private static final int ONE_HOUR_IN_MILLIS = 1000 * 60 * 60;

    private static final String SECRET_KEY = "secret";

    public static String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static <T> T extractClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //Getting a user and putting it in a map and using the method below
    public static String generateToken(final UserDto user) {
        final Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        return createToken(claims, user.getEmail());
    }

    //This method using the map of user and returns the specific token which configured with all token parameters
    // and the secret key + algorithm
    private static String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR_IN_MILLIS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    //Checking if token is still valid and equal to specific user's email
    public static boolean validateToken(final String token, final UserDetails user) {
        final String email = extractEmail(token);
        return email.equals(user.getUsername()) && !isTokenExpired(token);
    }

    //Checking if token has been expired
    private static boolean isTokenExpired(final String token) {

        return extractExpiration(token).before(new Date());
    }
}
