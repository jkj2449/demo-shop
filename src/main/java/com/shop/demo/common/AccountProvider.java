package com.shop.demo.common;

import com.shop.demo.domain.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AccountProvider {
    private AccountProvider() {}

    public static Account getAccount() {
        return (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
