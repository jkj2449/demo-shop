package com.shop.demo.domain.cart;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.demo.dto.cart.CartListResponseDto;
import com.shop.demo.dto.cart.QCartListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shop.demo.domain.cart.QCart.cart;
import static com.shop.demo.domain.item.QItem.item;

@Repository
public class CartRepositorySupportImpl extends QuerydslRepositorySupport implements CartRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CartRepositorySupportImpl(JPAQueryFactory jpaQueryFactory) {
        super(Cart.class);
        this.queryFactory = jpaQueryFactory;
    }

    public Cart getCart(final Long id) {
        return queryFactory.selectFrom(cart)
                .where(cart.id.eq(id))
                .fetchOne();
    }

    public Page<CartListResponseDto> searchCartList(final Long memberId, final Pageable pageable) {
        List<CartListResponseDto> list = queryFactory.select(new QCartListResponseDto(cart, item))
                .from(cart)
                .join(item).on(cart.itemId.eq(item.id))
                .where(cart.memberId.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(Wildcard.count)
                .from(cart)
                .join(item).on(cart.itemId.eq(item.id))
                .where(cart.memberId.eq(memberId))
                .fetchOne();

        PageImpl<CartListResponseDto> cartListResponseDtos = new PageImpl<>(list, pageable, count);
        return cartListResponseDtos;
    }
}
