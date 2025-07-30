package com.hansang.birthday.domain.auth.tokens;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long expiresIn;

    public static AuthTokens create(String accessToken, String refreshToken, String tokenType, Long expiresIn) {
        return new AuthTokens(accessToken, refreshToken, tokenType, expiresIn);
    }

    public AuthTokens withoutRefreshToken() {
        return new AuthTokens(this.accessToken, null, this.tokenType, this.expiresIn);
    }
}
