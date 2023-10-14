package com.aw.arbanware.domain.interestproduct;

import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.user.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(InterestProductKey.class)
public class InterestProduct {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;    //상품번호

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      // 회원번호

}
