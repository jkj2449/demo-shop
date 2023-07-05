package com.shop.demo.common;

import com.shop.demo.domain.member.Member;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityContextProvider {
    private SecurityContextProvider() {
    }

    public static Member getMember() {
        return (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
