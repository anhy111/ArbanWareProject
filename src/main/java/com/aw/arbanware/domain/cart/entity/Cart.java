package com.aw.arbanware.domain.cart.entity;

import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(CartKey.class)
@Getter @Setter
public class Cart {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;    // 상품 정보번호

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      // 회원번호

    private int quantity;       // 수량
}
