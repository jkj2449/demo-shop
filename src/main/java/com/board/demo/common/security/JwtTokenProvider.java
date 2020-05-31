package com.board.demo.common.security;

import com.board.demo.domain.account.Account;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public final class JwtTokenProvider {
    private static ObjectMapper MAPPER = getObjectMapper();
    private static final String secretKey = Base64.getEncoder().encodeToString(JwtProperties.SECRET.getBytes());
    private static String USER_KEY = "user";

    // JWT 토큰 생성
    public static String createToken(UserClaims userClaims) {
//        Claims claims = Jwts.claims().setSubject(email); // JWT payload 에 저장되는 정보단위
//        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .claim(USER_KEY, userClaims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
                .compact();
    }

    // 토큰에서 회원 정보 추출
    public static String getUserEmail(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        UserClaims userClaims = MAPPER.convertValue(claims.getBody().get(USER_KEY), UserClaims.class);
        return userClaims.getEmail();
    }

    // Request의 Header에서 token 값
    public static String resolveToken(HttpServletRequest request) {
        return request.getHeader(JwtProperties.HEADER_STRING);
    }

    // 토큰의 유효성 + 만료일자 확인
    public static boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static void setTokenInHeader(Account account) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        String token = JwtTokenProvider.createToken(MAPPER.convertValue(account, UserClaims.class));
        Objects.requireNonNull(response).addHeader(JwtProperties.HEADER_STRING, token);
    }

    @Getter
    static class UserClaims {
        private Long id;
        private String email;
        private String username;
        private String role;
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}
