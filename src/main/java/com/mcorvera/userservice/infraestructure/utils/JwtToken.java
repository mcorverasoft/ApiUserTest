package com.mcorvera.userservice.infraestructure.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtToken implements Serializable {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public String generateJwt(String subject) {
        //Milliseconds
        long currentTimeMillis = System.currentTimeMillis();
        //JWT configuration
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey());

        return builder.compact();
    }
    private Key getSigningKey() {
        byte[] keyBytes = this.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
