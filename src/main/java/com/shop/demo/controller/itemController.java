package com.shop.demo.controller;

import com.shop.demo.dto.item.ItemListResponseDto;
import com.shop.demo.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class itemController {

    private final ItemService itemService;
    @GetMapping("/api/v1/items")
    public List<ItemListResponseDto> find() {
        return itemService.findAll();
    }
}
