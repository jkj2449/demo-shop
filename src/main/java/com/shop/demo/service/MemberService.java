package com.shop.demo.service;

import com.shop.demo.common.security.JwtTokenProvider;
import com.shop.demo.domain.member.Member;
import com.shop.demo.domain.member.MemberRepository;
import com.shop.demo.domain.member.Role;
import com.shop.demo.dto.member.MemberSignInRequestDto;
import com.shop.demo.dto.member.MemberSignOutRequestDto;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import com.shop.demo.dto.member.MemberSingInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberSingInResponseDto signIn(final MemberSignInRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        jwtTokenProvider.createNewToken(member);

        return MemberSingInResponseDto.builder()
                .member(member)
                .build();
    }

    @Override
    public Member loadUserByUsername(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));
    }

    @Transactional
    public void signUp(final MemberSignUpRequestDto requestDto) {
        if (!requestDto.isMatchedPasswordConfirm()) {
            throw new IllegalArgumentException("패스워드 확인이 패스워드와 불일치");
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(Role.USER.getRole())
                .build();

        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
    }

    public void signOut(final MemberSignOutRequestDto requestDto) {
        jwtTokenProvider.deleteRefreshToken(requestDto.getEmail());
    }
}
