package com.shop.demo.common;

import com.shop.demo.domain.member.Member;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AccountProvider {
    private AccountProvider() {}

    public static Member getAccount() {
        return (Member)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
