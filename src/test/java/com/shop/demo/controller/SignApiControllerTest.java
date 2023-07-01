package com.shop.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SignApiControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    public void 회원가입된다() throws Exception {
        String email = "test@test.co.kr";
        String username = "test";
        String password = "1234";
        String passwordConfirm = "1234";

        MemberSignUpRequestDto requestDto = MemberSignUpRequestDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .passwordConfirm(passwordConfirm)
                .build();

        String url = "/api/v1/signUp";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void 패스워드와_패스워드_확인이_불일치_하다() throws Exception {
        String email = "test@test.co.kr";
        String username = "test";
        String password = "1234";
        String passwordConfirm = "124";

        MemberSignUpRequestDto requestDto = MemberSignUpRequestDto.builder()
                .email(email)
                .username(username)
                .password(password)
                .passwordConfirm(passwordConfirm)
                .build();

        String url = "/api/v1/signUp";

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}