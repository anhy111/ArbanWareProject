package com.aw.arbanware.domain.review;

import com.aw.arbanware.domain.common.DeleteYn;
import com.aw.arbanware.domain.product.Product;
import com.aw.arbanware.domain.product.ProductInfo;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue
    @Column(name = "REVIEW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_INFO_ID")
    private ProductInfo productInfo;

    private int rating;
    private String content;
    private Long attachFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn;
}
