package com.shop.demo.dto.member;

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
}
