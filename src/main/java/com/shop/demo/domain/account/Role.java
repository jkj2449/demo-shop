package com.shop.demo.domain.account;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN(RoleProperties.ROLE_ADMIN), USER(RoleProperties.ROLE_USER);

    private final String role;

    public String getRole() {
        return role;
    }

    public static class RoleProperties {
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
    }
}
