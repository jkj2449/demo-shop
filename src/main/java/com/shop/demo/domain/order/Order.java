package com.shop.demo.domain.order;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long memberId;
    @Column(length = 500, nullable = false)
    private String address;
    @Column(length = 50, nullable = false)
    private String payment;
    @Column(length = 50, nullable = false)
    private String cardNumber;
}
