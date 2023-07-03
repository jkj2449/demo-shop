package com.shop.demo.controller;

import com.shop.demo.dto.item.ItemListResponseDto;
import com.shop.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class itemController {

    private final ItemService itemService;
//    @GetMapping("/api/v1/items")
//    public List<ItemListResponseDto> find() {
//        return itemService.findAll();
//    }
    
    @GetMapping("/api/v1/items")
    public Page<ItemListResponseDto> findById(@PageableDefault Pageable pageable) {
        return itemService.findAll(pageable);
    }
}
