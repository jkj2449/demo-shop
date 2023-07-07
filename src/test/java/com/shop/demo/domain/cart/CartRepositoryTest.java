package com.shop.demo.domain.cart;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;

    @Test
    public void 장바구니_불러오기() {
        Long id = 1L;
        Long memberId = 1L;

        cartRepository.save(Cart.builder()
                .id(id)
                .memberId(memberId)
                .build());

        Cart cart = cartRepository.getCart(id);
        assertThat(cart.getId()).isEqualTo(id);
        assertThat(cart.getMemberId()).isEqualTo(memberId);
    }
}