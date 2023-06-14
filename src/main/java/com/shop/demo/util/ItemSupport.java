package com.shop.demo.util;

import com.shop.demo.dto.item.ItemSaveRequestDto;
import com.shop.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemSupport implements ApplicationRunner {
    private final ItemService itemService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> imgUrlList = Arrays.asList(
                "/img/bread-4170338_640.jpg",
                "/img/ring-2405145_640.jpg",
                "/img/stainless-878337_640.jpg"
        );

        for(int i=1; i<11; i++) {
            Collections.shuffle(imgUrlList);

            ItemSaveRequestDto dto = ItemSaveRequestDto.builder()
                    .name("상품" + i)
                    .price((long) (i * 100))
                    .description("상품 설명" + i)
                    .imagePath(imgUrlList.get(0))
                    .build();

            itemService.save(dto);

        }

    }
}
