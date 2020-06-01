package com.board.demo.domain.account;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN(RoleProperties.ROLE_ADMIN), USER(RoleProperties.ROLE_USER);

    private final String role;

    public String getRole() {
        return role;
    }

    public static class RoleProperties {
        private static final String ROLE_ADMIN = "ROLE_ADMIN";
        private static final String ROLE_USER = "ROLE_USER";
    }
}
