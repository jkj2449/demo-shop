package com.board.demo.dto.account;

import com.board.demo.domain.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountSingInResponseDto {
    private Long id;
    private String email;
    private String username;

    @Builder
    public AccountSingInResponseDto(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.username = account.getUsername();
    }
}
