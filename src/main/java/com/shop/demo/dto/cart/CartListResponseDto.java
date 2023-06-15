package com.shop.demo.dto.cart;

import com.shop.demo.domain.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartListResponseDto {
    private Long id;
    private Long memberId;
    private Long itemId;

    @Builder
    public CartListResponseDto(Cart entity) {
        this.id = entity.getId();
        this.memberId = entity.getMemberId();
        this.itemId = entity.getItemId();
    }
}
