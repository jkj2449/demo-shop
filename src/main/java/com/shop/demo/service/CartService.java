package com.shop.demo.service;

import com.shop.demo.common.AccountProvider;
import com.shop.demo.domain.cart.Cart;
import com.shop.demo.domain.cart.CartRepository;
import com.shop.demo.domain.item.Item;
import com.shop.demo.domain.item.ItemRepository;
import com.shop.demo.dto.cart.CartListResponseDto;
import com.shop.demo.dto.cart.CartSaveRequestsDto;
import com.shop.demo.dto.item.ItemResponseDto;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    public List<CartListResponseDto> findByMemberId(final Long id) {
        if(!AccountProvider.getAccount().getId().equals(id)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }


        List<Cart> cartList = cartRepository.findByMemberId(id);
        List<Long> itemIdList = cartList.stream().map(Cart::getItemId).collect(Collectors.toList());

        List<Item> itemList = itemRepository.findAllById(itemIdList);

        return cartList
                .stream()
                .map(v -> {
                    CartListResponseDto dto = CartListResponseDto.builder().entity(v).build();

                    ItemResponseDto matchedItem = itemList.stream()
                            .findAny()
                            .filter(item -> item.getId().equals(v.getItemId()))
                            .map(ItemResponseDto::new)
                            .orElse(null);

                    dto.setItem(matchedItem);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(final CartSaveRequestsDto dto) {
        if(!AccountProvider.getAccount().getId().equals(dto.getMemberId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return cartRepository.save(dto.toEntity()).getId();
    }
}
