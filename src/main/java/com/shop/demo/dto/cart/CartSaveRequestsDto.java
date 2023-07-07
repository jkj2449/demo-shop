package com.shop.demo.dto.cart;

import com.shop.demo.domain.cart.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
