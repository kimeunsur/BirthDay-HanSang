package com.hansang.birthday.domain.auth.tokens;

import org.springframework.http.ResponseCookie;

import java.time.Duration;

public class TokenCookieUtil {
    public static ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Lax")
                .build();

    }

    public static ResponseCookie createExpiredTokenCookie() {
        return ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0) // 만료
                .build();
    }
}
