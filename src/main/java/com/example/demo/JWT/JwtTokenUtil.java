package com.example.demo.JWT;

import com.example.demo.Models.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import java.util.Date;
@Component
@Slf4j
public class JwtTokenUtil {

    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "lpktyc/aVUV6iQ9QcfnC5Zph9x7EKf8ERkdkplDf6z394kKVJQrL/i+TP91FnJEmEU6rnriUYnKNcW7G9Gxh0Q==";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    public String generateToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }


    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }


    public boolean validateToken(String authToken) {
        log.info("Validating token: {}", authToken);
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(authToken);

            // Check if the token is expired
            if (isTokenExpired(authToken)) {
                log.error("Expired JWT token");
                return false;
            }

            log.info("Token is valid. User: {}", claimsJws.getBody().getSubject());
            return true; // Valid token
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty: {}", ex.getMessage());
        }
        return false; // Invalid token
    }


}
