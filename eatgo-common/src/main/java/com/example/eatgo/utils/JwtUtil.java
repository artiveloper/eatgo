package com.example.eatgo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private String secret;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    public String createToken(Long userId, String name) {
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

}
