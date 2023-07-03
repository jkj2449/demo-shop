package com.shop.demo.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSignOutRequestDto {
    private String email;

    @Builder
    public MemberSignOutRequestDto(String email) {
        this.email = email;
    }
}
