package com.board.demo.common.security;

public final class JwtProperties {
    public static final String SECRET = "secret_key";
    public static final long EXPIRATION_TIME = 30 * 60 * 1000L;
    public static final String HEADER_AUTHORIZATION = "Authorization";
}
