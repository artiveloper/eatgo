package com.example.eatgo.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class JwtUtilTest {

    @Test
    void createToken() throws Exception {
        String secret = "12345678901234567890123456789012";

        JwtUtil jwtUtil = new JwtUtil(secret);

        String token = jwtUtil.createToken(1L, "ravi");

        assertThat(token, containsString("."));
    }

}