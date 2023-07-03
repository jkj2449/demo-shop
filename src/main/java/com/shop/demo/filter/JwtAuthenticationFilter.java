package com.shop.demo.filter;

import com.shop.demo.common.security.JwtTokenProvider;
import com.shop.demo.common.security.Token;
import com.shop.demo.domain.member.Member;
import com.shop.demo.service.MemberService;
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

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Token token = jwtTokenProvider.resolveToken(request);

        try {
            if (token.getAccessToken() != null && jwtTokenProvider.isValidAccessToken(token.getAccessToken())) {
                this.setAuthentication(token.getAccessToken());

            } else if (token.getRefreshToken() != null && jwtTokenProvider.isValidRefreshToken(token.getRefreshToken())) {
                String email = jwtTokenProvider.getEmail(token.getRefreshToken());
                Member member = memberService.loadUserByUsername(email);

                Token newToken = jwtTokenProvider.createToken(member);
                jwtTokenProvider.setTokenInHeaderAndCookie(newToken);

                this.setAuthentication(newToken.getAccessToken());
            }
        } catch (Exception e) {
            log.error("jwt token check error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        Member member = jwtTokenProvider.toMember(token);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member, "", member.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
