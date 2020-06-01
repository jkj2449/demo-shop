package com.board.demo.common;

import com.board.demo.domain.account.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AccountProvider {
    private AccountProvider() {}

    public static Account getAccount() {
        return (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
