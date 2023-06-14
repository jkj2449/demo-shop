package com.shop.demo.dto.item;

import com.shop.demo.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String imagePath;

    @Builder
    public ItemSaveRequestDto(Long id, String name, Long price, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .description(description)
                .imagePath(imagePath)
                .build();
    }
}
