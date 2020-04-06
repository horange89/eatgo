package kr.co.fastcampus.eatgo.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

class JwtUtilTests {

    @Test
    public void createToken() {
        String scret = "12345678901234567890123456789012";

        JwtUtil jwtUtil = new JwtUtil(scret);

        String token = jwtUtil.createToken(1004, "John");

        assertThat(token, containsString("."));
    }
}