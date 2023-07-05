package com.shop.demo.service;

import com.shop.demo.common.SecurityContextProvider;
import com.shop.demo.domain.cart.Cart;
import com.shop.demo.domain.cart.CartRepository;
import com.shop.demo.domain.item.Item;
import com.shop.demo.domain.item.ItemRepository;
import com.shop.demo.dto.cart.CartDeleteRequestsDto;
import com.shop.demo.dto.cart.CartListResponseDto;
import com.shop.demo.dto.cart.CartSaveRequestsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public Page<CartListResponseDto> findByMemberId(final Long memberId, final Pageable pageable) {
        if (!SecurityContextProvider.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Page<Cart> cartList = cartRepository.findByMemberId(memberId, pageable);

        return cartList
                .map(CartListResponseDto::new);
    }

    @Transactional
    public Long save(final CartSaveRequestsDto dto) {
        if (!SecurityContextProvider.getMember().getId().equals(dto.getMemberId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new IllegalArgumentException("해당 아이템이 없습니다. id=" + dto.getItemId()));

        return cartRepository.save(dto.toEntity(item)).getId();
    }

    @Transactional
    public void deleteAll(final CartDeleteRequestsDto requestsDto) {
        cartRepository.deleteByIdIn(requestsDto.getCartIdList());
    }
}