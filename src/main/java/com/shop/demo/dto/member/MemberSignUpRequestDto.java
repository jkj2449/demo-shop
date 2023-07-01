package com.shop.demo.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignUpRequestDto {
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;

    public boolean isMatchedPasswordConfirm() {
        return password.equals(passwordConfirm);
    }

    @Builder
    public MemberSignUpRequestDto(String email, String username, String password, String passwordConfirm) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
