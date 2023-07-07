package com.shop.demo.service;

import com.shop.demo.common.SecurityContextProvider;
import com.shop.demo.domain.cart.CartRepository;
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

    public Page<CartListResponseDto> findByMemberId(final Long memberId, final Pageable pageable) {
        if (!SecurityContextProvider.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return cartRepository.searchCartList(memberId, pageable);
    }

    @Transactional
    public Long save(final CartSaveRequestsDto dto) {
        if (!SecurityContextProvider.getMember().getId().equals(dto.getMemberId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return cartRepository.save(dto.toEntity()).getId();
    }

    @Transactional
    public void deleteAll(final CartDeleteRequestsDto requestsDto) {
        cartRepository.deleteByIdIn(requestsDto.getCartIdList());
    }
}