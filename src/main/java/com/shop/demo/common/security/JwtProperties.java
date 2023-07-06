package com.shop.demo.common.security;

import java.time.Duration;
import java.util.Base64;

public final class JwtProperties {
    public static final String SECRET_KEY = "secret_key";
    public static final String ENCODE_SECRET_KEY = Base64.getEncoder().encodeToString(JwtProperties.SECRET_KEY.getBytes());
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(10).toMillis();
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = Duration.ofMinutes(30).toMillis();
    public static final String HEADER_AUTHORIZATION_KEY = "Authorization";
    public static final String ACCESS_TOKEN_KEY = "access";
    public static final String REFRESH_TOKEN_KEY = "refresh";


}
