package com.aw.arbanware.domain.cart;

import com.aw.arbanware.domain.product.ProductImageKey;
import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.Member;

import javax.persistence.*;

@Entity
@IdClass(CartKey.class)
public class Cart {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private int quantity;
}
