package kr.co.fastcampus.eatgo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtil {

    private Key key;

    public JwtUtil(String scret) {
        this.key = Keys.hmacShaKeyFor(scret.getBytes());
    }

    //JJWT를 사용한 토큰 생성
    public String createToken(long userId, String name) {
        String token = Jwts.builder().claim("userId", userId).claim("name", name)
                .signWith(key, SignatureAlgorithm.HS256).compact();

        return token;
    }
}
