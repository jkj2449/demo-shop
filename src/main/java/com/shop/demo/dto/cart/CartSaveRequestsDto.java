package com.shop.demo.dto.cart;

import com.shop.demo.domain.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartSaveRequestsDto {
    private Long memberId;
    private Long itemId;

    public Cart toEntity() {
        return Cart.builder()
                .memberId(memberId)
                .itemId(itemId)
                .build();
    }
}
