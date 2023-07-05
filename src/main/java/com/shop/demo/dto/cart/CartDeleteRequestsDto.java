package com.shop.demo.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDeleteRequestsDto {
    private List<Long> cartIdList;
}
