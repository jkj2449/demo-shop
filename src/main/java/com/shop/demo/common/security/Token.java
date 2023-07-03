package com.shop.demo.common.security;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public Token(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}