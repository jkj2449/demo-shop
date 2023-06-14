package com.shop.demo.dto.member;

import com.shop.demo.domain.account.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberSingInResponseDto {
    private Long id;
    private String email;
    private String username;

    @Builder
    public MemberSingInResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.username = member.getUsername();
    }
}
