package com.shop.demo.dto.cart;

import com.shop.demo.domain.cart.Cart;
import com.shop.demo.dto.item.ItemResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class CartListResponseDto {
    private Long id;
    private Long memberId;
    private Long itemId;
    private ItemResponseDto item;

    @Builder
    public CartListResponseDto(Cart entity) {
        this.id = entity.getId();
        this.memberId = entity.getMemberId();
        this.itemId = entity.getItemId();
    }
}
