package com.shop.demo.domain.cart;

import com.shop.demo.dto.cart.CartListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CartRepositorySupport {
    Cart getCart(final Long id);

    Page<CartListResponseDto> searchCartList(final Long memberId, final Pageable pageable);
}
