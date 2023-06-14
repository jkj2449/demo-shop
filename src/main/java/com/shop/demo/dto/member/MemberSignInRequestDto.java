package com.shop.demo.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignInRequestDto {
    private String email;
    private String password;
}
