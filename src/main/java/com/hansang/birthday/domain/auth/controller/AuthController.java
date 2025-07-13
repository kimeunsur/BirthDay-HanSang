package com.hansang.birthday.domain.auth.controller;

import com.hansang.birthday.domain.auth.service.AuthService;
import com.hansang.birthday.domain.auth.tokens.AuthTokens;
import com.hansang.birthday.domain.auth.tokens.TokenCookieUtil;
import com.hansang.birthday.domain.auth.dto.LoginResponse;
import com.hansang.birthday.domain.auth.service.KakaoService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "로그인 및 인증 관련 API")
public class AuthController {
    private final KakaoService kakaoService;
    private final AuthService authService;

    @GetMapping("/api/users/login/oauth/kakao")
    public ResponseEntity<LoginResponse> kakaoLogin(@RequestParam String code,
                                                    HttpServletRequest request) {
        try{
            LoginResponse fullResponse = kakaoService.kakaoLogin(code);
            ResponseCookie responseCookie = TokenCookieUtil.createRefreshTokenCookie(
                    fullResponse.getAuthToken().getRefreshToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                    .body(new LoginResponse(
                            fullResponse.getUserId(),
                            fullResponse.getAuthToken().withoutRefreshToken()));
        }catch(NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found");
        }

    }
    @PostMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        authService.logout(request);
        ResponseCookie expiredTokenCookie = TokenCookieUtil.createExpiredTokenCookie();
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE,expiredTokenCookie.toString())
                .build();
    }

    @PostMapping("/api/auth/reissue")
    public ResponseEntity<AuthTokens> reissue(HttpServletRequest request) {
        AuthTokens newAccessToken = authService.reissueAccessToken(request);
        return ResponseEntity.ok(newAccessToken.withoutRefreshToken());
    }

    @Hidden
    @GetMapping("api/auth/test")
    public String test(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth: {}", auth);
        return "hello,world!";
    }


}
