package com.example.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    private String secret;

    public JwtUtil(String secret) {
        this.secret = secret;
    }

    public String createToken(Long userId, String name, Long restaurantId) {
        System.out.println("======== in createToken");
        String token = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name)
                .claim("restaurantId", restaurantId != null ? restaurantId : "")
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(this.secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }

}
