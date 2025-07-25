package com.hansang.birthday.domain.auth.dto;


import com.hansang.birthday.domain.auth.tokens.AuthTokens;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private AuthTokens authToken;

    public LoginResponse(Long userId, AuthTokens authToken) {
        this.userId = userId;
        this.authToken = authToken;
    }
}
