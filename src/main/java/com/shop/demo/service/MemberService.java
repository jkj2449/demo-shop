package com.shop.demo.service;

import com.shop.demo.domain.account.Role;
import com.shop.demo.common.security.JwtTokenProvider;
import com.shop.demo.domain.account.Member;
import com.shop.demo.domain.account.MemberRepository;
import com.shop.demo.dto.member.MemberSignInRequestDto;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import com.shop.demo.dto.member.MemberSingInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSingInResponseDto signIn(MemberSignInRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        JwtTokenProvider.setTokenInHeader(member);

        return MemberSingInResponseDto.builder()
                .member(member)
                .build();
    }

    @Override
    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));;

        return member;
    }

    @Transactional
    public void signUp(MemberSignUpRequestDto requestDto) {
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(Role.USER.getRole())
                .build();

        member.encodePassword(passwordEncoder);
        memberRepository.save(member);
    }
}
