package com.shop.demo.controller;

import com.shop.demo.common.security.JwtProperties;
import com.shop.demo.common.security.JwtTokenProvider;
import com.shop.demo.common.security.Token;
import com.shop.demo.domain.member.Member;
import com.shop.demo.dto.member.MemberSignInRequestDto;
import com.shop.demo.dto.member.MemberSignOutRequestDto;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import com.shop.demo.dto.member.MemberSingInResponseDto;
import com.shop.demo.exception.JwtTokenNotValidException;
import com.shop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignApiController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인
    @PostMapping("/api/v1/signIn")
    public MemberSingInResponseDto signIn(@RequestBody final MemberSignInRequestDto requestDto) {
        return memberService.signIn(requestDto);
    }

    @PostMapping("/api/v1/signUp")
    public ResponseEntity<Void> signUp(@RequestBody final MemberSignUpRequestDto requestDto) {
        memberService.signUp(requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/signOut")
    public ResponseEntity<Void> signOut(@RequestBody final MemberSignOutRequestDto requestDto) {
        memberService.signOut(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/refresh")
    public ResponseEntity<MemberSingInResponseDto> refresh() {
        try {
            Token token = jwtTokenProvider.createValidToken();
            Member member = token.toMember(token.getAccessToken(), JwtProperties.ACCESS_TOKEN_KEY);
            return new ResponseEntity<>(new MemberSingInResponseDto(member), HttpStatus.OK);
        } catch (JwtTokenNotValidException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
