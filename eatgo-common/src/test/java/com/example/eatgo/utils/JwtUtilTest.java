package com.example.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilTest {

    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    void createToken() throws Exception {

        String token = jwtUtil.createToken(1L, "ravi");

        assertThat(token, containsString("."));
    }

    @Test
    void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsIm5hbWUiOiJyYXZpIn0.qaSxNusDZw4D18iIpu6y4z7dzaNKW_wi98h9FOXM290";
        Claims claims = jwtUtil.getClaims(token);

        assertEquals(claims.get("name"), "ravi");
        assertEquals(claims.get("userId", Long.class), 1L);
    }

}