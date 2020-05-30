package com.board.demo.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountSignUpRequestDto {
    private String email;
    private String username;
    private String password;
}
