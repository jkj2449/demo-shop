package com.shop.demo.domain.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 아이템저장_불러오기() {
        String name = "이름";
        Long price = 100L;

        itemRepository.save(
                Item.builder()
                        .name(name)
                        .price(price)
                        .build());

        List<Item> itemList = itemRepository.findAll();

        Item item = itemList.get(0);
        assertThat(item.getPrice()).isEqualTo(price);
        assertThat(item.getName()).isEqualTo(name);
    }
}