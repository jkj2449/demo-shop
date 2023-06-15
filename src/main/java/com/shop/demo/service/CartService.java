package com.shop.demo.service;

import com.shop.demo.domain.cart.CartRepository;
import com.shop.demo.dto.cart.CartListResponseDto;
import com.shop.demo.dto.cart.CartSaveRequestsDto;
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

    public List<CartListResponseDto> findByMemberId(final Long id) {
        return cartRepository.findByMemberId(id)
                .stream()
                .map(v -> CartListResponseDto.builder().entity(v).build())
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(final CartSaveRequestsDto dto) {
        return cartRepository.save(dto.toEntity()).getId();
    }
}
