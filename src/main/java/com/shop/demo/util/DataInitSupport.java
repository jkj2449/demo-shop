package com.shop.demo.util;

import com.shop.demo.dto.item.ItemSaveRequestDto;
import com.shop.demo.dto.member.MemberSignUpRequestDto;
import com.shop.demo.service.ItemService;
import com.shop.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInitSupport implements ApplicationRunner {
    private final ItemService itemService;
    private final MemberService memberService;

    @Override
    public void run(ApplicationArguments args) {
        initMember();
        initItem();
    }

    private void initMember() {
        MemberSignUpRequestDto dto = MemberSignUpRequestDto
                .builder()
                .email("test@test.co.kr")
                .username("test")
                .password("1234")
                .passwordConfirm("1234")
                .build();

        memberService.signUp(dto);
    }

    private void initItem() {
        List<String> imgUrlList = Arrays.asList(
                "/img/bread-4170338_640.jpg",
                "/img/ring-2405145_640.jpg",
                "/img/stainless-878337_640.jpg"
        );

        IntStream.rangeClosed(10, 50)
                .forEach(i -> {
                    Collections.shuffle(imgUrlList);

                    ItemSaveRequestDto dto = ItemSaveRequestDto.builder()
                            .name("상품" + i)
                            .price((long) (i * 100))
                            .description("상품 설명" + i)
                            .imagePath(imgUrlList.get(0))
                            .build();

                    itemService.save(dto);
                });
    }
}
