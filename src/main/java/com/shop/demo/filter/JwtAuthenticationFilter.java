package com.shop.demo.filter;

import com.shop.demo.common.security.JwtProperties;
import com.shop.demo.common.security.JwtTokenProvider;
import com.shop.demo.common.security.Token;
import com.shop.demo.domain.member.Member;
import com.shop.demo.exception.JwtTokenNotValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private static final String[] excludePath = {"/api/v1/signIn", "/api/v1/signUp", "/api/v1/refresh"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Token token = jwtTokenProvider.createValidToken();
            this.setAuthentication(token);

        } catch (JwtTokenNotValidException e) {
            log.info("jwt not invalid", e);
        } catch (Exception e) {
            log.error("JwtAuthenticationFilter error", e);
            response.setHeader("error", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).filter(v -> v.equals(path)).findAny().isPresent();
    }

    private void setAuthentication(Token token) {
        Member member = token.toMember(token.getAccessToken(), JwtProperties.ACCESS_TOKEN_KEY);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
