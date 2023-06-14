package com.shop.demo.dto.item;

import com.shop.demo.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private String imagePath;

    @Builder
    public ItemResponseDto(Item entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
        this.description = entity.getDescription();
        this. imagePath = entity.getImagePath();
    }
}
