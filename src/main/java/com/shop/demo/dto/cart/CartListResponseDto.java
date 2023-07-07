package com.shop.demo.dto.cart;

import com.querydsl.core.annotations.QueryProjection;
import com.shop.demo.domain.cart.Cart;
import com.shop.demo.domain.item.Item;
import com.shop.demo.dto.item.ItemResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class CartListResponseDto {
    private Long id;
    private Long memberId;
    private ItemResponseDto item;

    @QueryProjection
    public CartListResponseDto(Cart cart, Item item) {
        this.id = cart.getId();
        this.memberId = cart.getMemberId();
        this.item = new ItemResponseDto(item);
    }
}
