package com.aw.arbanware.domain.cart.entity;

import com.aw.arbanware.domain.product.entity.ProductInfo;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(CartKey.class)
@Getter @Setter
public class Cart {

    @Id
    private Long productInfoId;

    @Id
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productInfoId", updatable = false, insertable = false)
    private ProductInfo productInfo;    // 상품 정보번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", updatable = false, insertable = false)
    private Member member;      // 회원번호

    private int quantity;       // 수량
}
