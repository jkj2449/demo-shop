package com.shop.demo.controller;

import com.shop.demo.dto.cart.CartListResponseDto;
import com.shop.demo.dto.cart.CartSaveRequestsDto;
import com.shop.demo.dto.item.ItemListResponseDto;
import com.shop.demo.service.CartService;
import com.shop.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CartController {

    private final CartService cartService;
    @GetMapping("/api/v1/cart/{memberId}")
    public List<CartListResponseDto> find(@PathVariable("memberId") final Long memberId) {
        return cartService.findByMemberId(memberId);
    }

    @PostMapping("/api/v1/cart")
    public Long save(@RequestBody final CartSaveRequestsDto dto) {
        return cartService.save(dto);
    }

}
