package com.hansang.birthday.domain.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class RefreshTokenExtractor {
    private static final String REFRESH_COOKIE_FROM_COOKIE = "refreshToken";

    public static String fromCookie(HttpServletRequest request) {
        if(request.getCookies()==null)return null;
        for(Cookie cookie : request.getCookies()) {
            if(REFRESH_COOKIE_FROM_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
