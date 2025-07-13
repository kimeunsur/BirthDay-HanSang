package com.hansang.birthday.domain.auth.service;

import com.hansang.birthday.domain.auth.jwt.JwtTokenProvider;
import com.hansang.birthday.domain.auth.tokens.AuthTokens;
import com.hansang.birthday.domain.auth.tokens.AuthTokensGenerator;
import com.hansang.birthday.domain.auth.util.RefreshTokenExtractor;
import com.hansang.birthday.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    public void logout(HttpServletRequest request) {
        String refreshToken = RefreshTokenExtractor.fromCookie(request);
        if(refreshToken!=null) {
            userRepository.findByRefreshToken(refreshToken).ifPresent(user -> {
                user.setRefreshToken(null);
                userRepository.save(user);
            });
        }
        //log.info("로그아웃 및 refreshToken 삭제 완료");

    }
    public AuthTokens reissueAccessToken(HttpServletRequest request) {
        String refreshToken = RefreshTokenExtractor.fromCookie(request);
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token 없음");
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token 만료");
        }

        return userRepository.findByRefreshToken(refreshToken)
                .map(user -> authTokensGenerator.generateAccessOnly(user.getUserId().toString()))
                .orElseThrow(() -> new RuntimeException("해당 refresh token을 가진 유저 없음"));
    }

}
