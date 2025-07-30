package com.hansang.birthday.domain.auth.tokens;


import com.hansang.birthday.domain.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; //1시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일

    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokens generate(String userId) {
        long now = new Date().getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        String accessToken = jwtTokenProvider.accessTokenGenerate(userId, accessTokenExpiredAt);
        String refreshToken = jwtTokenProvider.refreshTokenGenerate(refreshTokenExpiredAt);

        return AuthTokens.create(accessToken,refreshToken,BEARER_TYPE,ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public AuthTokens generateAccessOnly(String userId) {
        long now = new Date().getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = jwtTokenProvider.accessTokenGenerate(userId, accessTokenExpiredAt);
        return AuthTokens.create(accessToken,null,BEARER_TYPE,ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }
}
