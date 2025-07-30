package com.hansang.birthday.domain.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    public JwtTokenProvider(@Value("${JWT_SECRET_KEY}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessTokenGenerate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject) //id
                .setExpiration(expiredAt)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String refreshTokenGenerate(Date expiredAt) {
        return Jwts.builder()
                .setExpiration(expiredAt)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token);
            log.info("JWT token validated");
            return true;
        }catch(SecurityException | MalformedJwtException e){
            log.warn("잘못된 JWT 서명입니다.");
        }catch(ExpiredJwtException e){
            log.warn("만료된 토큰입니다.");
        }catch(UnsupportedJwtException e){
            log.warn("지원하지 않는 토큰입니다.");
        }catch(IllegalArgumentException e){
            log.warn("잘못된 토큰입니다.");
        }
        return false;
    }
    public String getUserIdFromToken(String token) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }
    }



}
