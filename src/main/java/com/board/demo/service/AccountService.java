package com.board.demo.service;

import com.board.demo.domain.account.Role;
import com.board.demo.common.security.JwtTokenProvider;
import com.board.demo.domain.account.Account;
import com.board.demo.domain.account.AccountRepository;
import com.board.demo.dto.account.AccountSignInRequestDto;
import com.board.demo.dto.account.AccountSignUpRequestDto;
import com.board.demo.dto.account.AccountSingInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountSingInResponseDto signIn(AccountSignInRequestDto requestDto) {
        Account account = accountRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), account.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        JwtTokenProvider.setTokenInHeader(account);

        return AccountSingInResponseDto.builder()
                .account(account)
                .build();
    }

    @Override
    public Account loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 email이 없습니다."));;

        return account;
    }

    @Transactional
    public void signUp(AccountSignUpRequestDto requestDto) {
        Account account = Account.builder()
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .role(Role.USER.getRole())
                .build();

        account.encodePassword(passwordEncoder);
        accountRepository.save(account);
    }
}
