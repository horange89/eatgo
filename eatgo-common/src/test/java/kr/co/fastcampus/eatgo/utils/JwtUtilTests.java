package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.Is.is;

class JwtUtilTests {

    private static final String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken() {
        String token = jwtUtil.createToken(2020L, "Owner", 1004L);

        assertThat(token, containsString("."));
    }

    @Test
    public void getClaims() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIwMjAsIm5hbWUiOiJPd25lciIsInJlc3RhdXJhbnRJZCI6MTAwNH0.a5n4PWJ2-3yVyMaLGG0HSPXtH_mgpOvofpQ1OFkgDOQ";

        Claims claims = jwtUtil.getClaims(token);

        assertThat(claims.get("userId", Long.class), is(2020L));
        assertThat(claims.get("name"), is("Owner"));

    }
}