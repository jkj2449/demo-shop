package com.shop.demo.controller;

import com.shop.demo.dto.member.MemberSignInRequestDto;
import com.shop.demo.dto.member.MemberSignOutRequestDto;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import com.shop.demo.dto.member.MemberSingInResponseDto;
import com.shop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class SignApiController {
    private final MemberService memberService;

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

}
