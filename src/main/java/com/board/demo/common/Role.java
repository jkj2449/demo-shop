package com.board.demo.common;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String role;

    public String getRole() {
        return role;
    }
}
