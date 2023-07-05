package com.shop.demo.common.security;

import java.time.Duration;

public final class JwtProperties {
    public static final String SECRET_KEY = "secret_key";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(10).toMillis();
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30).toMillis();
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String ACCESS_TOKEN_KEY = "access";
    public static final String REFRESH_TOKEN_KEY = "refresh";


}
