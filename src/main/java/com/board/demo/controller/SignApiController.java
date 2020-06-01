package com.board.demo.controller;

import com.board.demo.dto.account.AccountSignInRequestDto;
import com.board.demo.dto.account.AccountSignUpRequestDto;
import com.board.demo.dto.account.AccountSingInResponseDto;
import com.board.demo.service.AccountService;
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
    private final AccountService accountService;

    // 로그인
    @PostMapping("/api/v1/singIn")
    public AccountSingInResponseDto signIn(@RequestBody AccountSignInRequestDto requestDto) {
        return  accountService.signIn(requestDto);
    }

    @PostMapping("/api/v1/signUp")
    public ResponseEntity<Void> signUp(@RequestBody AccountSignUpRequestDto requestDto) {
        accountService.signUp(requestDto);
        return ResponseEntity.ok().build();
    }

}
