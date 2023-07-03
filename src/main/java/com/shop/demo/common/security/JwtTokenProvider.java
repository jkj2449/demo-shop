package com.shop.demo.common.security;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.demo.domain.member.Member;
import io.jsonwebtoken.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public final class JwtTokenProvider {
    private static final ObjectMapper MAPPER = getObjectMapper();
    private static final String ENCODE_SECRET_KEY = Base64.getEncoder().encodeToString(JwtProperties.SECRET_KEY.getBytes());

    private final RedisTemplate redisTemplate;

    // 토큰에서 회원 정보 추출
    public Member toMember(final String accessToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(ENCODE_SECRET_KEY).parseClaimsJws(accessToken);
        UserClaims userClaims = MAPPER.convertValue(claims.getBody().get(JwtProperties.ACCESS_TOKEN_KEY), UserClaims.class);

        return MAPPER.convertValue(userClaims, Member.class);
    }

    public String getEmail(final String refreshToken) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(ENCODE_SECRET_KEY).parseClaimsJws(refreshToken);
        return claims.getBody().get(JwtProperties.REFRESH_TOKEN_KEY).toString();
    }

    public Token resolveToken(final HttpServletRequest request) {
        List<Cookie> cookies = request.getCookies() != null ? List.of(request.getCookies()) : Collections.emptyList();
        Cookie cookie = cookies.stream().findFirst()
                .filter(v -> v.getName().equals(JwtProperties.REFRESH_TOKEN_KEY))
                .orElse(null);

        String accessToken = request.getHeader(JwtProperties.HEADER_AUTHORIZATION_KEY);
        String refreshToken = cookie != null ? cookie.getValue() : null;

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void setTokenInHeaderAndCookie(final Token token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        Cookie cookie = new Cookie(JwtProperties.REFRESH_TOKEN_KEY, token.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
        response.addHeader(JwtProperties.HEADER_AUTHORIZATION_KEY, token.getAccessToken());
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean isValidAccessToken(final String token) {
        try {
            Jwts.parser().setSigningKey(ENCODE_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("token is expired", e);
            return false;
        }
    }

    public boolean isValidRefreshToken(final String token) {
        String email = this.getEmail(token);

        String key = List.of(JwtProperties.REFRESH_TOKEN_KEY, email).stream()
                .collect(Collectors.joining(":"));

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String refreshToken = valueOperations.get(key);

        return refreshToken != null;
    }

    public Token createToken(final Member member) {
        UserClaims userClaims = UserClaims.of(member);

        String accessToken = createAccessToken(userClaims);
        String refreshToken = createRefreshToken(userClaims);

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // JWT 토큰 생성
    private String createAccessToken(final UserClaims userClaims) {
        long now = System.currentTimeMillis();
        long expirationTime = now + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;

        return Jwts.builder()
                .claim(JwtProperties.ACCESS_TOKEN_KEY, userClaims) // 정보 저장
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, ENCODE_SECRET_KEY)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    private String createRefreshToken(final UserClaims userClaims) {
        long now = System.currentTimeMillis();
        long expirationTime = now + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;

        String token = Jwts.builder()
                .claim(JwtProperties.REFRESH_TOKEN_KEY, userClaims.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expirationTime))
                .signWith(SignatureAlgorithm.HS256, ENCODE_SECRET_KEY)
                .compact();

        String key = List.of(JwtProperties.REFRESH_TOKEN_KEY, userClaims.getEmail()).stream()
                .collect(Collectors.joining(":"));

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, token, JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return token;
    }

    public void deleteRefreshToken(final String email) {
        String key = List.of(JwtProperties.REFRESH_TOKEN_KEY, email).stream()
                .collect(Collectors.joining(":"));
        redisTemplate.delete(key);
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    @Getter
    @RequiredArgsConstructor
    static class UserClaims {
        private Long id;
        private String email;
        private String username;
        private String role;

        @Builder
        public UserClaims(Long id, String email, String username, String role) {
            this.id = id;
            this.email = email;
            this.username = username;
            this.role = role;
        }

        public static UserClaims of(final Member member) {
            return UserClaims.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .username(member.getUsername())
                    .role(member.getRole())
                    .build();

        }
    }

}
