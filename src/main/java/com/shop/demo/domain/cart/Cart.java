package com.shop.demo.domain.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @Column
    private Long itemId;

    @Builder
    public Cart(Long memberId, Long itemId) {
        this.memberId = memberId;
        this.itemId = itemId;
    }
}
